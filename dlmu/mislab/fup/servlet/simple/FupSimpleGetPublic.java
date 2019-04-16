package dlmu.mislab.fup.servlet.simple;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.param.SimpleGetPubParam;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.JsonBase;

/***
 * 需要参数：fl_no：文件编号 fl_ext：文件扩展名（不含。）
 * 返回一个Ok对象，其中额rspn.msg包含了Base64编码的文件内容
 */
@WebServlet(FupDict.FUP_SIMPLE_GET_PUBLIC)
public class FupSimpleGetPublic extends JsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		SimpleGetPubParam bean =new SimpleGetPubParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		if(bean.getFullname()==null){
			return new Err(FupSimpleError.FUP_SIMPLE_FILENAME_EMPTY);
		}
		String rtn = loadFileToBase64String(bean.getFullname().toString(), this.logger);
		if(rtn==null){
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_READ_FAILED);
		}
		return new Ok(rtn);
	}
	
	static String loadFileToBase64String(String sFlName, Logger logger){
		File file=SimpleTool.generateFullPath(sFlName).toFile();
		byte[] bytes=null;
		try{
			bytes=FileUtils.readFileToByteArray(file);
		}catch(Throwable e){
			logger.error(e.getMessage());
			return null;
		}
		return new Base64().encodeAsString(bytes);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}
}
