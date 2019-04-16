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

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.IFile;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupDownloadPublicParam;
import dlmu.mislab.tool.Str;
import dlmu.mislab.web.servlet.ServletBase;

/**
 * Download a file for anyone
 * Parameters need:
 * 1. fl_name: File name with ext
 * 2. att: is download as attachement. Default:false [optional]
 * 3. enc: encoding type of filename, default is "c" [optional]
 */
@WebServlet(FupDict.FUP_ACTION_DOWNLOAD_PUBLIC_FILE)
public class FupDownloadPubFile extends ServletBase{
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
		FupDownloadPublicParam bean=new FupDownloadPublicParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			this.redirectError(request, response, this.getErr().getMsg());
			return;
		}

		FupFileModel file=new FupFileModel();
		File f =new File(bean.getFullname());
		file.setFl_name(Str.getFileNameShort(f));
		file.setFl_ext(Str.getFileExt(f));
		file.setFl_name_local(FupDict.FUP_DEFAULT_PUBLIC_PATH + ConfigBase.PATH_DELIMETER + bean.getFullname());

		File downloadFile = new File(FupDict.FUP_PHYSICAL_ROOT+file.getFl_name_local());
		//		if(!downloadFile.exists()){
		//			downloadFile= new File(FupDict.FUP_PHYSICAL_ROOT+ ConfigBase.PATH_DELIMETER +FupDict.FUP_DEFAULT_FILE_NOT_FOUND_ICON);
		//		}

		FupDownloadPubFile.setHeaders(getServletContext(),downloadFile, file, bean,response);
		if(FupDownloadPubFile.outputFile(bean.getIs_big(), downloadFile,response,this.logger)==false){
			//this.redirectError(request, response, "文件输出失败"); //response has already closed
			logger.error("文件输出失败");
			return;
		}

	}//end of doGet

	static void setHeaders(ServletContext context, File downloadFile, IFile finfo, FupDownloadPublicParam bean,HttpServletResponse response) {
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

