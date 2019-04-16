package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.orm.Bn;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.SimpleFileBean;
import dlmu.mislab.fup.model.param.SimpleRenameParam;

/**
 * Servlet implementation class FupSelectFree
 */
@WebServlet(FupDict.FUP_SIMPLE_RENAME)
public class FupSimpleRename extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleRenameParam bean = new SimpleRenameParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		SimpleFileBean sb=new SimpleFileBean();
		sb.setFl_no(bean.getFl_no());
		sb.setFl_name(bean.getFl_name());
		if(!Bn.Update(sb)){
			return new Err(FupSimpleError.FUP_SIMPLE_DB_RENAME_FAILED);
		}
		return new Ok("重命名成功");
	}


}
