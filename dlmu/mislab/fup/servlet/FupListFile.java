package dlmu.mislab.fup.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.dao.FupFileDao;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.param.FupListParam;
import dlmu.mislab.fup.model.response.FupListRspn;
import dlmu.mislab.validation.Validator;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

/**
 * List all files within a designated folder
 * Servlet implementation class FupListServlet
 */
@WebServlet(FupDict.FUP_ACTION_LIST_FILE)
public class FupListFile extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}
	
	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupListParam bean=new FupListParam();
		if(!this.parseBeanFromRequest(bean, request)){
			return this.getErr();
		}
		
		Err err=Validator.validateBean(bean,ConfigBase.PACKAGE_VALIDATION_FIELD);
		if(err!=null){
			return err;
		}
			
		FupFileDao dao=new FupFileDao();
		List<FupFileModel> list=dao.getChildFiles(bean.getFd_no(), this.getUserInfo().getUserId());
				
		FupListRspn rspn=new FupListRspn();
		rspn.setChild_files(list);
		return new Ok(rspn);
	}       

}


