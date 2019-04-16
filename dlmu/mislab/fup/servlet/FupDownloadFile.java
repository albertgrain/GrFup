package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import dlmu.mislab.common.DictBase;
import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.IFile;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupDownloadParam;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.tool.Str;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.servlet.SessionBase;

/**
 * Servlet implementation class DownloadFile
 * Parameters need:
 * 1. fno: file no [necessary]
 * 2. att: is download as attachement. Default:false [optional]
 * 3. enc: encoding type of filename, default is "c" [optional]
 */
@WebServlet(FupDict.FUP_ACTION_DOWNLOAD_FILE)
public class FupDownloadFile extends SessionBase{
	private static final long serialVersionUID = 1L;

	//private HttpServletRequest request;
	//private HttpServletResponse response;


	public static final HashMap<String, String> ExtToMIME=new HashMap<String,String>(30);
	static{
		ExtToMIME.put("unknown","application/octet-stream");
		ExtToMIME.put("7z","application/x-compressed");
		ExtToMIME.put("doc","application/msword");
		ExtToMIME.put("docx","application/msword");
		ExtToMIME.put("jpg","image/jpeg");
		ExtToMIME.put("pdf","application/x-pdf");
		ExtToMIME.put("ppt","application/mspowerpoint");
		ExtToMIME.put("pptx","application/mspowerpoint");
		ExtToMIME.put("rar","application/x-compressed");
		ExtToMIME.put("txt","text/plain");
		ExtToMIME.put("xls","application/x-msexcel");
		ExtToMIME.put("xlsx","application/x-msexcel");
		ExtToMIME.put("zip","application/x-zip-compressed");
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {
		//this.request=request;
		//this.response=response;

		FupDownloadParam bean=new FupDownloadParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			this.redirectError(request, response, DictBase.HTPP_PARAMETERS_PARSING_FAILED);
			return;
		}
		
		Err result=Validator.validateBean(bean,FupDict.FUP_VALIDATION_PACKAGE_NAME);
		if(result!=null){
			System.out.println(result.getMsg());
			this.logger.error(result.getMsg());
			this.redirectError(request, response, result.getMsg());
			return;
		}
		FupFileModel file=this.getFileInfo(bean.getFno());
		if(file==null) {
			String msg="获取文件信息失败";
			System.out.println(msg);
			this.logger.error(msg);
			this.redirectError(request, response, msg);
			return;
		}

		if(this.getUserInfo()==null){
			System.out.println("请先登录");
			this.redirectError(request, response, "请先登录");
			return;
		}

		if(
				file.getOwner_id()==FupDict.FUP_USER_SHARE_OWNER_NO ||
				file.getFd_no()==FupDict.FUP_SHARE_FOLDER_NO ||
				file.getOwner_id().equals(this.getUserInfo().getUserId()) ||
				//file.getOwner_id().equals(FupDict.FUP_SYSTEM_USER_ID) || //Changed on 2017-04-20 
				//FupDict.FUP_SYSTEM_USER_ID.equals(this.getUserInfo().getUserId())
				FupDict.PUBLIC_UPLOAD_ROLE.equals(this.getUserInfo().getRole()) ||
				FupDict.FUP_MANAGER_ROLE.equals(this.getUserInfo().getRole())
				){
			this.logger.debug("Going to download file:" +file.getFl_no());
		}else{
			System.out.println("无访问权限");
			this.logger.error("无访问权限");
			this.redirectError(request, response, "无访问权限");
			return;
		}

		// if you want to use a relative path to context root:
		//String relativePath = getServletContext().getRealPath("");
		File downloadFile = new File(FupDict.FUP_PHYSICAL_ROOT+file.getFl_name_local());

		FupDownloadFile.setHeaders(getServletContext(),downloadFile, file, bean,response);
		if(FupDownloadFile.outputFile(bean.getIs_big(), downloadFile,response,this.logger)==false){
			//this.redirectError(request, response, "文件输出失败");
			System.out.println("文件输出失败");
			return;
		}

	}//end of doGet

	private FupFileModel getFileInfo(Integer fno){
		if(fno==null){
			this.logger.error("文件编号为空");
			return null;
		}
		FupFileModel file=Bn.SelectOne(FupFileModel.class, "select * from tb_fup_file where fl_no=?", fno);

		if(file==null){
			this.logger.error("没有找到编号为["+ fno +"]的文件信息");
			return null;
		}

		if(Str.isNullOrEmpty(file.getFl_name_local())){
			this.logger.error("根据文件编号没有找到编号为["+ fno +"]的文件物理路径信息");
			return null;
		}

		return file;
	}

	static void setHeaders(ServletContext context, File downloadFile, IFile finfo, FupDownloadParam bean,HttpServletResponse response) {
		// gets MIME type of the file
		String mimeType = context.getMimeType(downloadFile.getName());
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType =ExtToMIME.get(finfo.getFl_ext());
			if(mimeType==null){
				mimeType =ExtToMIME.get("unknown");
			}
		}
		System.out.println("MIME type: " + mimeType);

		// modifies response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// forces download
		if(bean.getAtt()){
			String encodedHeader=AttchementFileNameEncoder.encode(bean.getEnc(), finfo.getFl_name(), finfo.getFl_ext());
			response.setHeader("Content-Disposition", encodedHeader);
		}
	}

	static boolean outputFile(boolean isBig, File downloadFile, HttpServletResponse response,Logger logger){
		FileInputStream inStream = null;
		try{
			inStream=new FileInputStream(downloadFile);
		}catch(Exception e){
			logger.error("从服务器读取文件失败:"+downloadFile.getName()+" 具体原因："+e.getMessage());
			try{
				inStream.close();
			}catch(Exception e2){}
			return false;
		}

		// obtains response's output stream
		OutputStream outStream;
		try{
			outStream=response.getOutputStream();
		}catch(Exception e){
			logger.error("打开输出流失败"+" 具体原因："+e.getMessage());
			try{
				inStream.close();
			}catch(Exception e2){}
			return false;
		}

		byte[] buffer =null;
		if(isBig){
			buffer=new byte[FupDict.FUP_DEFAULT_DOWN_FILE_CACHE_SIZE_BIG];
		}else{
			buffer=new byte[FupDict.FUP_DEFAULT_DOWN_FILE_CACHE_SIZE_SMALL];
		}
		int bytesRead = -1;

		try {
			while ((bytesRead = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			logger.error("输出二进制数据失败"+" 具体原因："+e.getMessage());
			return false;
		}finally{
			try{
				inStream.close();
				outStream.close();
			}catch(Exception e){
				logger.error("关闭流失败");
				return false;
			}
		}// end of try
		return true;
	}
}//end of class

