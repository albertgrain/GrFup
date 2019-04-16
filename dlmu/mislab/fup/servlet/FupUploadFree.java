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

import dlmu.mislab.common.LogicError;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupError;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupUploadParam;
import dlmu.mislab.tool.Str;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.JsonBase;

/**
 * Servlet implementation class FupFreeUpload
 */
@Deprecated
@WebServlet(FupDict.FUP_ACTION_FREE_UPLOAD)
public class FupUploadFree extends JsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") < 0)) {
			return Err.getInstance(FupError.FUP_NO_FILE);
		}

		List<FileItem> fileItems=FupUploadFile.generateFileItemList(FupDict.FUP_PHYSICAL_TEMP, request,this.logger);
		if(fileItems==null){
			return Err.getInstance(FupError.FUP_READ_HTTP_FAILED);
		}

		String relativePath=request.getParameter("relative_path");
		if(Str.isNullOrEmpty(relativePath)){
			relativePath=FupDict.FUP_DEFAULT_FREE_PATH;
		}
		FupUploadParam formParam=new FupUploadParam();

		List<FupFileModel> fileList=FupUploadFile.generateFilesInfo(fileItems,formParam,"guest",this.logger,relativePath);
		if(fileList==null){
			return Err.LOGIN_REQUIRED;
		}else if(fileList.size()==0){
			return Err.getInstance(FupError.FUP_NO_FILE);
		}

		List<FupFileModel> fileList3=this.writeFiles(fileItems, formParam, fileList);
		if(fileList3.size()!=fileList.size()){
			return Err.getInstance(FupError.FUP_WRITE_FILE_FAILED);
		}

		if(fileList.size()!=fileList.size()){
			return Err.getInstance(FupError.FUP_PARTIAL_SUCCESS);
		}

		return new Ok("Uploaded");
	}

	private List<FupFileModel> writeFiles(List<FileItem> fileItems, FupUploadParam formParam, List<FupFileModel> fileList2){
		List<FupFileModel> rtn=new LinkedList<FupFileModel>();
		Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = i.next();
			if (!fi.isFormField()) {
				String filename=null;
				try {
					filename = URLDecoder.decode(fi.getName(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("Failed to decode filename as UTF-8");
					filename = "uploaded";
				}
				FupFileModel flModel=getFileModelFromListForWriteToDisk(fileList2,filename);
				if(flModel!=null){
					LogicError wrErr=FupUploadFile.writeOneFile(fi,flModel,this.logger);
					if(wrErr==null){
						rtn.add(flModel);
					}
				}
			}//End of if (fi.isFormField())
		}// End of while
		return rtn;
	}

	private FupFileModel getFileModelFromListForWriteToDisk(List<FupFileModel> fileList2, String filename){
		for(FupFileModel model : fileList2){
			if(filename.equals(model.getFl_name())){
				return model;
			}
		}
		return null;
	}

}
