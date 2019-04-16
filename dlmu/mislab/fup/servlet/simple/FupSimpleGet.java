package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.servlet.SessionJsonBase;

import dlmu.mislab.fup.FupDict;

/***
 * 需要参数：fl_no：文件编号 fl_ext：文件扩展名（不含。）
 * 返回一个Ok对象，其中额rspn.msg包含了Base64编码的文件内容
 */
@WebServlet(FupDict.FUP_SIMPLE_GET)
public class FupSimpleGet extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupSimpleGetPublic proxy= new FupSimpleGetPublic();
		return proxy.doJsonGet(request, response);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}


}
