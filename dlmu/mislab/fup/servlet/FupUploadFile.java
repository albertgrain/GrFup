package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.common.LogicError;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupError;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupUploadParam;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.validation.ValidationException;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.OkList;
import dlmu.mislab.web.servlet.SessionJsonBase;



/**
 * 
 * By GuRui on 2014-12-17 上午2:06:22
 * Parameters:
 * 1. fd_no: folder_no, default is 0 [optional]
 * 2. show_type: "link" or "img", default is "link" [optional]
 * 3. extra: extra String type info needed for before and after uploading callback. [optonal]
 *
 */
@WebServlet(name="FupUploadServlet",urlPatterns=FupDict.FUP_ACTION_UPLOAD_FILE)
public class FupUploadFile extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//uploadRoot = context.getRealPath(DictBase.FUP_PHYSICAL_ROOT);
		String contentType = request.getContentType();
		if ((contentType.indexOf("multipart/form-data") < 0)) {
			return Err.getInstance(FupError.FUP_NO_FILE);
		}

		List<FileItem> fileItems=FupUploadFile.generateFileItemList(FupDict.FUP_PHYSICAL_TEMP, request,this.logger);
		if(fileItems==null){
			return Err.getInstance(FupError.FUP_READ_HTTP_FAILED);
		}

		FupUploadParam formParam=this.parseFormParam(fileItems);
		formParam.setOwner_id(this.getUserInfo().getUserId());
		if(formParam.getFd_no()==null){
			formParam.setFd_no(0);
		}
		try{
			Validator.validate(formParam,FupDict.FUP_VALIDATION_PACKAGE_NAME);
		}catch(ValidationException err){
			this.logger.error(err.getMsg());
			return Err.getInstance(FupError.FUP_INVALID_PARAMETERS);
		}

		//userId must exist!
		List<FupFileModel> fileList=FupUploadFile.generateFilesInfo(fileItems,formParam,this.getUserInfo().getUserId(),this.logger,null);
		if(fileList==null){
			return Err.LOGIN_REQUIRED;
		}else if(fileList.size()==0){
			return Err.getInstance(FupError.FUP_NO_FILE);
		}

		if(FupDict.CALLBACK_UPLOAD_FILE!=null){
			if(!FupDict.CALLBACK_UPLOAD_FILE.beforeUpload(fileList,formParam.getExtra())){
				return new Err(FupDict.CALLBACK_UPLOAD_FILE.getLastError());
			}
		}

		List<FupFileModel> fileList2=FTools.saveUploadFileModelToDataBase(fileList);
		if(fileList2.size()==0){
			return Err.getInstance(FupError.FUP_SAVE_TO_DB_FAILED);
		}

		List<FupFileModel> fileList3=this.writeFiles(fileItems, formParam, fileList2);
		if(fileList3.size()!=fileList2.size()){
			LogicError err=FTools.removeFileModelFromDb(fileList2, fileList3);
			if(err!=null){
				logger.error("Failed to remove file model from database");
			}
			return Err.getInstance(FupError.FUP_WRITE_FILE_FAILED);
		}

		if(fileList.size()!=fileList2.size()){
			return Err.getInstance(FupError.FUP_PARTIAL_SUCCESS);
		}

		if(FupDict.CALLBACK_UPLOAD_FILE!=null){
			if(!FupDict.CALLBACK_UPLOAD_FILE.afterUpload(fileList3,formParam.getExtra())){
				return new Err(FupDict.CALLBACK_UPLOAD_FILE.getLastError());
			}
		}
		return new OkList<FupFileModel>(fileList3);
	}



	/**
	 * 
	 * By GuRui on 2015-4-15 上午11:08:13
	 * @param fileItems
	 * @param formParam owner_id,fl_name,fd_no=0
	 * @return null:login required, 0 size: no file found
	 */
	static List<FupFileModel> generateFilesInfo(List<FileItem> fileItems, FupUploadParam formParam, String userId, Logger logger, String relativePath){
		List<FupFileModel> rtn=new LinkedList<FupFileModel>();
		Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = i.next();
			if (!fi.isFormField()) {
				FupFileModel flModel=new FupFileModel();

				flModel.setShow_type(formParam.getShow_type());
				flModel.setFd_no(formParam.getFd_no());

				flModel.setFl_size(fi.getSize());
				flModel.setUpload_date(new Date());
				String filename;
				try {
					filename = URLDecoder.decode(fi.getName(),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					logger.error("Failed to decode filename as UTF-8");
					filename = "uploaded";
				}
				flModel.setFl_name(filename);
				if(filename.indexOf(".")>0){
					flModel.setFl_ext(filename.substring(filename.lastIndexOf(".")+1).toLowerCase());
				}
				
				Integer isNotShare=Bn.selectScalar(Integer.class, "select fd_no from tb_fup_folder where fd_no=? and owner_id is null ", formParam.getFd_no());
				if(isNotShare==null){ //If owner_id=null , means it is not a share folder. Otherwise, the owner_id is left to be null
					flModel.setOwner_id(userId);
				}

				/**
				 * RelativePath is assigned when doing Free Upload. Otherwise, it is null.
				 */
				if(relativePath!=null){
					flModel.setFl_name_local(relativePath+ConfigBase.PATH_DELIMETER+filename);
				}

				rtn.add(flModel);
			}//End of if (fi.isFormField())
		}// End of while
		return rtn;
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
						flModel.setFl_name_local(null);
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
				if(model.getFd_no()!=FupDict.FUP_MY_FOLDER_NO){ //It is a physical file info
					model.setFl_no(model.getFl_no_link());
				}
				return model;
			}
		}
		return null;
	}

	private FupUploadParam parseFormParam(List<FileItem> fileItems) {
		FupUploadParam rtn=new FupUploadParam();
		Iterator<FileItem> i = fileItems.iterator();
		while (i.hasNext()) {
			FileItem fi = i.next();
			if (fi.isFormField()) {
				if(FupDict.FUP_TAG_FOLDER_NO.equals(fi.getFieldName())){
					rtn.setFd_no(fi.getString());
				}else if(FupDict.FUP_TAG_SHOW_TYPE.equals(fi.getFieldName())){
					rtn.setShow_type(fi.getString());
				}else if("extra".equals(fi.getFieldName())){
					rtn.setExtra(fi.getString());
				}
				rtn.setOwner_id(this.getUserInfo().getUserId());
			}
		}
		logger.debug("FupUploadParam:"+rtn.toString());
		return rtn;
	}



	/**
	 * 上传文件准备
	 * By GuRui on 2014-12-17 上午2:05:36
	 * @param uploadRoot 上传根目录
	 * @param request
	 * @return
	 */
	static List<FileItem> generateFileItemList(String uploadTempPath,HttpServletRequest request,Logger logger){
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(FupDict.FUP_MAX_MEM_SIZE);
		factory.setRepository(new File(uploadTempPath));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(FupDict.FUP_MAX_FILE_SIZE);

		List<FileItem> fileItems=null;

		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			logger.error(e.getMessage());
			return null;
		}
		return fileItems;
	}

	/**
	 * 将文件写道磁盘上
	 * By GuRui on 2014-12-17 上午2:05:17
	 * @param fi
	 * @param fupBase
	 * @param uploadRoot
	 * @return
	 */
	static LogicError writeOneFile(FileItem fi,FupFileModel flModel,Logger logger){
		File file = new File(FupDict.FUP_PHYSICAL_ROOT,flModel.getFl_name_local());
		file.getParentFile().mkdirs();

		try {
			fi.write(file);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return FupError.FUP_WRITE_FILE_FAILED;
		}
		return null;
	}

	/**
	 * 
	 * By GuRui on 2014-12-15 上午12:36:23
	 * @param startPath 所有上传文件的根目录
	 * @param targetPath 与表对应的类型目录
	 * @param key 根据表主键值建立的目录
	 * @return 返回建立完成的目录全名。建立失败返回null
	 */
	//	private String createPath(String startPath,String targetPath, String key){
	//		File file=new File(startPath,targetPath);
	//		if(!file.exists()){
	//			if(!file.mkdir()){
	//				return null;
	//			}
	//		}
	//		File file2=new File(file.getPath(),key);
	//		if(!file2.exists()){
	//			if(!file2.mkdir()){
	//				return null;
	//			}
	//		}
	//		return file2.getPath();
	//	}


}

