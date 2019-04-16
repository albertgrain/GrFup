package dlmu.mislab.fup.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dlmu.mislab.fup.*;
import dlmu.mislab.fup.dao.FupFolderDao;
import dlmu.mislab.fup.model.FupFolderModel;
import dlmu.mislab.fup.model.param.FupListParam;
import dlmu.mislab.fup.model.response.FupListRspn;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;
/**
 * Servlet implementation class FupListFileServlet
 */
@WebServlet(FupDict.FUP_ACTION_LIST_FOLDER)
public class FupListFolder extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupListParam bean=new FupListParam();
		if(!this.parseBeanFromRequest(bean, request)){
			return this.getErr();
		}
				
		FupFolderDao dao=new FupFolderDao();
		List<FupFolderModel> list=dao.getAllVisibleFolders(this.getUserInfo().getUserId());
				
		FupListRspn rspn=new FupListRspn();
		rspn.setChild_folders(list);
		return new Ok(rspn);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}

}
