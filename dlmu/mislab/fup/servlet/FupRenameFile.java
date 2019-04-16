// By GuRui on 2015-4-24 上午8:20:55
package dlmu.mislab.fup.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.dao.FupFileDao;
import dlmu.mislab.fup.model.param.FupRenameFileParam;
import dlmu.mislab.validation.ValidationException;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

@WebServlet(FupDict.FUP_ACTION_RENAME_FILE)
public class FupRenameFile extends SessionJsonBase {
	/**
	 * By GuRui on 2015-4-24 上午8:55:08
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupRenameFileParam bean=new FupRenameFileParam();
		if(!this.parseBeanFromRequest(bean, request)){
			return this.getErr();
		}
		try{
			Validator.validate(bean,ConfigBase.PACKAGE_VALIDATION_FIELD);
		}catch(ValidationException e){
			return Err.getInstance(e);
		}
		FupFileDao dao=new FupFileDao();
		if(!dao.rename(bean.getFl_no(), bean.getFl_name())){
			return new Err("重命名失败");
		}
		
		return new Ok("重命名成功!");
	}

}
