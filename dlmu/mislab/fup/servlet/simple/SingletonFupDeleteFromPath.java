// By GuRui on 2018-3-16 上午11:01:25
package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.servlet.SessionJsonBase;

/***
 * 从FupDict.FUP_RESTRICTED_UPLOAD_PATH指定的目录删除文件
 * 此Servlet为单例，一次仅允许一人删除（删除不可与上传同时进行）
 * By GuRui on 2018-3-16 上午11:05:20
 *
 */
@WebServlet(FupDict.FUP_RESTRICTED_SINGLETON_DELETE)
public class SingletonFupDeleteFromPath  extends SessionJsonBase {
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
		return null;
	}

}
