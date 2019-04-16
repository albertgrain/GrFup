// By GuRui on 2015-4-24 上午8:21:13
package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.dao.FupFileDao;
import dlmu.mislab.fup.dao.FupFolderDao;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupCopyFileParam;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;


@WebServlet(FupDict.FUP_ACTION_COPY_FILE)
public class FupCopyFile extends SessionJsonBase {

	/**
	 * By GuRui on 2015-4-24 上午9:56:57
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupCopyFileParam bean=new FupCopyFileParam();
		if(!this.parseBeanFromRequest(bean, request)){
			return this.getErr();
		}
		if(bean.getF_fd_no().equals(bean.getT_fd_no())){
			return new Err("源文件夹和目标文件夹相同，不能拷贝");
		}
		Err err=Validator.validateBean(bean,ConfigBase.PACKAGE_VALIDATION_PARAM);
		if(err!=null){
			return err;
		}
		
		IResponse result=this.copy(bean.getF_fd_no(), bean.getT_fd_no(), bean.getFl_no_list(), this.getUserInfo().getUserId());
		if(result==null){
			return new Ok("拷贝文件成功!");
		}else{
			return result;
		}

	}
	
	private IResponse copy(Integer fromFolder, Integer toFolder, String fileNoList, String ownerId){

		FupFolderDao daofd=new FupFolderDao();
		if(!daofd.isOwner(toFolder, ownerId)){
			return new Err("无权写入目标文件夹");
		}
		
		FupFileDao dao=new FupFileDao();
		List<FupFileModel> fileList= dao.SelectFilesByFileNoList(fileNoList);
		if(fileList==null || fileList.size()==0){
			return new Err("待复制文件类表为空");
		}

		boolean isCurrentUserOwner=daofd.isOwner(fromFolder, ownerId); //If source owner is not current user, copy physical file first
		
		List<FupFileCopyModel> copyList=FTools.saveCopiedFileModelToDataBase(fileList,toFolder,ownerId,isCurrentUserOwner); //Append info to DB
		if(copyList==null){
			return new Err("向数据写入文件信息时出错");
		}
		
		if(!isCurrentUserOwner){ //If source owner is not current user, copy physical file
			if(!this.copyfile(copyList)){
				return new Err("拷贝物理文件失败");
			}
		}
		
		return new Ok("文件复制成功");
	}
	
	private boolean copyfile(List<FupFileCopyModel> copyList){
		for(FupFileCopyModel item:copyList){
			File from=new File(FupDict.FUP_PHYSICAL_ROOT,item.getFileSource());
			File to=new File(FupDict.FUP_PHYSICAL_ROOT,item.getFileDest());
			try {
				FileUtils.copyFile(from, to);
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
}

class FupFileCopyModel{
	private String fileSource;
	private String fileDest;
	
	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileDest() {
		return fileDest;
	}

	public void setFileDest(String fileDest) {
		this.fileDest = fileDest;
	}
	
}
