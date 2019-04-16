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
import dlmu.mislab.fup.model.param.SimpleRemoveParam;

/**
 * Servlet implementation class FupSelectFree
 */
@WebServlet(FupDict.FUP_SIMPLE_REMOVE)
public class FupSimpleRemove extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleRemoveParam bean= new SimpleRemoveParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}

		return SimpleRemove.removeFile(bean.getFl_no(), this.getUserInfo().getUserId(), this.getUserInfo().getRole());
	}


}
