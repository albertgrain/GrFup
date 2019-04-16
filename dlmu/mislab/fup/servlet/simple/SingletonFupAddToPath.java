package dlmu.mislab.fup.servlet.simple;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.param.SimpleAddParam;

/***
 * 上传文件到FupDict.FUP_RESTRICTED_UPLOAD_PATH指定的目录
 * 此Servlet为单例，一次仅允许一人上传（上传不可与删除同时进行）
 * By GuRui on 2018-3-16 上午11:06:25
 *
 */
@WebServlet(FupDict.FUP_RESTRICTED_SINGLETON_ADD)
public class SingletonFupAddToPath extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(!SingletonSignal.INSTANCE.getToken()){
			return new Err("资源未被释放，暂时不能执行上传操作，请稍后重试");
		}
		
		SimpleAddParam bean = new SimpleAddParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request,FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		
		ParsedFile pFile=null;
		try{
			pFile=SimpleTool.parseImgDataInfo(bean);
		}catch(Exception e){
			return new Err(e.getMessage());
		}
		
		int size=SimpleTool.writeFileToDisk(pFile.base64File, pFile.info.getFl_name(), FupDict.FUP_RESTRICTED_UPLOAD_PATH);
		if(size==-1){
			return new Err("文件写入磁盘失败");
		}
		return new Ok("文件成功上传");
	}
	

       

}
