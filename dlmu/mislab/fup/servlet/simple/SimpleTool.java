package dlmu.mislab.fup.servlet.simple;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupException;
import dlmu.mislab.fup.model.SimpleFileBean;
import dlmu.mislab.fup.model.param.SimpleAddParam;
import dlmu.mislab.tool.Str;


class ParsedFile{
	SimpleFileBean info;
	String base64File;
	int size;
}


class SimpleTool {

	////////////// For FUP Add  /////////////////
	private static Logger logger=LoggerFactory.getLogger(SimpleTool.class);
	private static final Pattern IMG_DATA_PATTERN = Pattern.compile("^data:.*[/].*;.*[,]$");
	
	public static ParsedFile parseImgDataInfo(SimpleAddParam bean) throws FupException{
		if(Str.isNullOrEmpty(bean.getFullname())){
			throw new FupException("文件名名为空");
		}
		
		if(bean.getFullname().lastIndexOf(".")<=0){
			throw new FupException("文件名中必须包含扩展名");
		}
			
		String[] flData=bean.getData().split(",");
		if(flData==null || flData.length!=2){
			throw new FupException("接收到的上传文件数据格式错误");
		}
		
		SimpleFileBean beanAdd=copyParamToBean(bean, bean.getFullname());
		beanAdd.setMime(flData[0]+",");
		Matcher matcher = IMG_DATA_PATTERN.matcher(beanAdd.getMime());
		if(!matcher.matches()){
			throw new FupException("上传文件数据的头格式错误");  //正确入： data:image/jpeg;base64,
		}
				
		ParsedFile rtn=new ParsedFile();
		rtn.base64File=flData[1];
		rtn.info=beanAdd;
		return rtn;
	}
	
	/***
	 * 
	 * By GuRui on 2018-3-16 下午4:18:33
	 * @param pm
	 * @param fullname 文件全名, 如: C:/path/goodday/aa.doc
	 * @return
	 */
	private static SimpleFileBean copyParamToBean(SimpleAddParam pm, String fullname){
		//String flNamePart=fullname.substring(0, fullname.lastIndexOf("."));
		String flExtPart=fullname.substring(fullname.lastIndexOf(".")+1);
		File f=new File(fullname);
		SimpleFileBean rtn=new SimpleFileBean();
		rtn.setFl_name(f.getName());
		rtn.setFl_ext(flExtPart);
		rtn.setShow_type(pm.getShow_type());
		rtn.setFl_memo(pm.getFl_memo());
		rtn.setFd_no(pm.getFd_no());
		return rtn;
	}
		
	static String generateLocalName(SimpleFileBean bean, boolean isPrivate){
		StringBuilder buf=new StringBuilder(200);
		buf.append('\\');
		if(isPrivate){
			buf.append(FupDict.FUP_FOLDER_MY_NAME).append('\\').append(bean.getOwner_id());
		}else{
			buf.append(FupDict.SIMPLE_FOLDER_NAME);
		}
		buf.append("\\").append(bean.getFl_no()).append('.').append(bean.getFl_ext());
		return buf.toString();
	}
	

	/***
	 * 将base64编码的字符串写入指定目录(目录的根必须是docshare)
	 * By GuRui on 2018-3-16 下午3:53:22
	 * @param base64Str 编码为base64的文件体
	 * @param filename 文件名（含扩展名）
	 * @param paths 指定目录,如[ "my","11111"] -> /docshare/my/1111
	 * @return
	 */
	static int writeFileToDisk(String base64Str, String filename, String... paths){
		Path folder= Paths.get(FupDict.FUP_PHYSICAL_ROOT, paths);
		if(!Files.isDirectory(folder)){
			try {
				Files.createDirectories(folder);
			} catch (IOException e) {
				logger.error("创建"+folder.toString()+"目录失败");
				return -1;
			}
		}
		Path fullname= folder.resolve(filename);
		return writeByteToDisk(base64Str, fullname);
	}
	
	/***
	 * 将base64编码的字符串写入指定目录
	 * By GuRui on 2018-3-16 下午3:53:49
	 * @param base64Str 编码为base64的文件体
	 * @param filename 完整文件名（含扩展名）构成的path对象
	 * @return
	 */
	private static int writeByteToDisk(String base64Str, Path fullname){
		byte[] bytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Str);
		int size=bytes.length;

		try{
			Files.write(fullname, bytes);
			//Files.write(Paths.get(FupDict.FUP_PHYSICAL_ROOT, FupDict.SIMPLE_FOLDER_NAME, filename), bytes);
		}catch(IOException | SecurityException e){
			logger.error(e.getMessage());
			return -1;
		}
		return size;
	}
	
	//////////For FUP Remove  /////////////////
	static Path generateFullPath(SimpleFileBean bean){
		if(bean==null || bean.getFl_name_local()==null){
			return null;
		}
		return Paths.get(FupDict.FUP_PHYSICAL_ROOT, bean.getFl_name_local());
	}
	
	static boolean removeFile(Path path){
		if(path==null){
			return false;
		}
		try {
			return Files.deleteIfExists(path);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	//////////For FUP Get  /////////////////
	static final HashMap<String, String> ExtToMIME=new HashMap<String,String>(30);
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
	
	static void setHeaders(SimpleFileBean bean, ServletContext context, HttpServletResponse response, boolean isDownload) {
		// gets MIME type of the file
		String mimeType = context.getMimeType(bean.getFl_ext());
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType =ExtToMIME.get("unknown");
		}
		
		// modifies response
		response.setContentType(mimeType);
		response.setContentLength(bean.getFl_size());

		// forces download
		if(isDownload){
			String encodedHeader=AttchementFileNameEncoder.encode(bean.getEnc(), bean.getFl_name(), bean.getFl_ext());
			response.setHeader("Content-Disposition", encodedHeader);
		}
	}
	
	static boolean outputFile(File downloadFile, HttpServletResponse response){
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

		byte[] buffer = new byte[FupDict.FUP_SIMPLE_DOWN_FILE_CACHE_SIZE];
		
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
	
	
	//////////////For FUP GetPub  /////////////////
	static Path generateFullPath(String fileName){
		return Paths.get(FupDict.FUP_PHYSICAL_ROOT, FupDict.SIMPLE_FOLDER_NAME, fileName);
	}


}
