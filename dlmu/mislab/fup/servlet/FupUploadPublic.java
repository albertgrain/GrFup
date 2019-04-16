package dlmu.mislab.fup.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.common.LogicError;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupError;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.FupFilePubModel;
import dlmu.mislab.tool.Str;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.OkList;
import dlmu.mislab.web.servlet.SessionJsonBase;

/**
 * 管理员上传公共文件。
 * 需要以具有FupDict.PUBLIC_UPLOAD_ROLE角色的用户登录。
 * 如果需要对上传文件改名，需要提供以FupDict.FUP_TAG_NEW_FILE_NAMES为标签的新文件名列表，每个文件名以,号分隔，文件名应包含扩展名
 * 上传后的文件存放在FupDict.FUP_DEFAULT_PUBLIC_PATH确定的目录下
 */
@WebServlet(FupDict.FUP_ACTION_UPLOAD_PUBLIC)
public class FupUploadPublic extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(!FupDict.PUBLIC_UPLOAD_ROLE.equals(this.getUserInfo().getRole())){
			return new Err("用户类型:"+this.getUserInfo().getRole()+"没有上传公共图片的权限!");
		}
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") < 0)) {
			return Err.getInstance(FupError.FUP_NO_FILE);
		}


		List<FileItem> fileItems=FupUploadFile.generateFileItemList(FupDict.FUP_PHYSICAL_TEMP, request,this.logger);
		String[] flNames=null;
		String flNamesStr=this.parseFormParam(fileItems);

		if(fileItems==null){
			return Err.getInstance(FupError.FUP_READ_HTTP_FAILED);
		}

		if(!Str.isNullOrEmpty(flNamesStr)){
			flNames=flNamesStr.split(",");
		}

		//		if(flNames!=null && (fileItems.size()-1)!=flNames.length){ //fileItems contains flNames, so minus 1
		//			return new Err("上传文件数量和重命名列表中文件个数不相同，上传被取消");
		//		}

		List<FupFilePubModel> rtn=this.writeFiles(fileItems, flNames);

		//		if(flNames!=null && flNames.length!=rtn.size()){
		//			return Err.getInstance(FupError.FUP_PARTIAL_SUCCESS);
		//		}

		return new OkList<FupFilePubModel>(rtn);
	}

	private String parseFormParam(List<FileItem> fileItems) {
		Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = i.next();
			if (fi.isFormField()) {
				if(FupDict.FUP_TAG_NEW_FILE_NAMES.equals(fi.getFieldName())){
					try {
						return fi.getString("UTF-8");
					} catch (UnsupportedEncodingException e) {
						return "";
					}
				}
			}
		}
		return "";
	}

	private List<FupFilePubModel> writeFiles(List<FileItem> fileItems, String[] flNames){
		List<FupFilePubModel> rtn=new LinkedList<FupFilePubModel>();
		Iterator<FileItem> itr = fileItems.iterator();
		int i=0;
		while (itr.hasNext()) {
			FileItem fi = itr.next();
			if (!fi.isFormField()) {
				String filename=null;
				if(flNames!=null){
					if(i>=flNames.length){
						filename="";
					}else{
						filename=flNames[i];
					}
					if(filename.indexOf('.')<=0){
						filename += fi.getName().substring(fi.getName().lastIndexOf('.'));
					}
				}else{
					try {
						filename = URLDecoder.decode(fi.getName(),"UTF-8");
					} catch (UnsupportedEncodingException e) {
						logger.error("Failed to decode filename as UTF-8");
						filename = "uploaded_"+i;
					}
				}
				FupFileModel flModel=new FupFileModel();
				flModel.setFl_name_local(FupDict.FUP_DEFAULT_PUBLIC_PATH +ConfigBase.PATH_DELIMETER+filename);
				if(flModel!=null){
					LogicError wrErr=FupUploadFile.writeOneFile(fi,flModel,this.logger);
					if(wrErr==null){
						FupFilePubModel m=new FupFilePubModel();
						m.setFl_name(filename);
						rtn.add(m);
					}
				}
				i++;
			}//End of if (fi.isFormField())
		}// End of while
		return rtn;
	}

}

