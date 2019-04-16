package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.common.IJson;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupError;
import dlmu.mislab.fup.dao.FupFileDao;
import dlmu.mislab.fup.dao.FupFileRspnDao;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupDeleteFileParam;
import dlmu.mislab.fup.model.response.FupFileRspn;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

/**
 * 只有MY目录下的文件可以被删除。
 * 如果删除文件已被链接，则应提示应先删除链接，再删除本文件。
 * Servlet implementation class FupDeleteServlet
 */
@WebServlet(FupDict.FUP_ACTION_DELETE_FILE)
public class FupDeleteFile extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupDeleteFileParam bean=new FupDeleteFileParam();
		if(!this.parseBeanFromRequest(bean, request)){
			this.logger.error("Parse bean from request failed.");
			return this.getErr();
		}
		Err err=Validator.validateBean(bean,FupDict.FUP_VALIDATION_PACKAGE_NAME);
		if(err!=null){
			this.logger.error("Validate parameters failed.");
			return err;
		}

		LinkedList<Integer> fnos=FTools.convertStrFileNoListToIntFileNoList(bean.getFl_no_list());
		if(fnos==null || fnos.size()==0){
			this.logger.error("File list to delete is emtpy.");
			return Err.getInstance(FupError.FUP_PARSE_TO_DELETE_FILE_NOS_ERROR);
		}

		if(FupDict.CALLBACK_DELETE_FILE!=null){
			if(!FupDict.CALLBACK_DELETE_FILE.beforeUpload(fnos,bean.getExtra())){
				return new Err(FupDict.CALLBACK_DELETE_FILE.getLastError());
			}
		}

		FupFileRspnDao dao=new FupFileRspnDao();
		List<FupFileRspn> result=dao.findFileWithLink(fnos);

		IResponse rspnDelete=null;
		if(FupDict.FUP_NO_LINK_FILE){ //Delete all link file by force
			if(result!=null){
				for(FupFileRspn fl: result){
					fnos.addFirst(fl.getFl_no());
				}
			}
			rspnDelete= this.deleteBatch(fnos);
		}else{
			if(result==null || result.size()==0){
				rspnDelete= this.deleteBatch(fnos);
			}else{
				StringBuilder buf=new StringBuilder(result.size()*100);
				buf.append("待删除文件存在链接文件，无法删除。请先删除链接文件:");
				for(FupFileRspn file : result){
					buf.append("\n /").append(file.getFd_name()).append("[").append(file.getFd_no()).append("]/").append(file.getFl_name()).append("[").append(file.getFl_no()).append("]");
				}

				return new Err(buf.toString()); // Found file with link, wait for user choice.
			}
		}

		if(rspnDelete instanceof Ok){
			if(FupDict.CALLBACK_DELETE_FILE!=null){
				if(!FupDict.CALLBACK_DELETE_FILE.afterUpload(fnos,bean.getExtra())){
					return new Err(FupDict.CALLBACK_DELETE_FILE.getLastError());
				}
			}
		}
		return rspnDelete;
	}

	public IResponse deleteBatch(List<Integer> fileNoList){
		FupFileDao dao=new FupFileDao();

		List<FupFileModel> fileList=dao.deleteBatch(fileNoList, this.getUserInfo().getUserId());
		if(fileList==null){
			return new Err("从数据库中删除文件信息失败。请检查文件的所有者是否是当前用户。");
		}

		LinkedList<Integer> rtn = new LinkedList<Integer>();
		for(FupFileModel bean:fileList){
			if(bean.getFl_no_link()==0 && bean.getOwner_id().equals(this.getUserInfo().getUserId())){
				if(this.physicalDeleteOneFile(bean.getFl_name_local())){
					rtn.add(bean.getFl_no());
				}
			}
		}

		ReturnDeleteFileNos rds= new ReturnDeleteFileNos();
		rds.deleted_files=rtn;
		return new Ok(rds);
	}

	private boolean physicalDeleteOneFile(String physicalFileName){
		File file=new File(FupDict.FUP_PHYSICAL_ROOT,physicalFileName);
		if(file.delete()){
			return true;
		}else{
			logger.info("文件信息清理成功，但从磁盘删除失败。 文件路径:"+file.getAbsolutePath());
			return false;
		}
	}

}


class ReturnDeleteFileNos implements IJson{
	public List<Integer> deleted_files;
}
