package dlmu.mislab.fup.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.dao.FupFolderDao;
import dlmu.mislab.fup.model.FupFolderModel;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

/**
 * Servlet implementation class FupDeleteFolder
 */
@WebServlet(FupDict.FUP_ACTION_DELETE_FOLDER)
public class FupDeleteFolder extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FupFolderModel bean=new FupFolderModel();
		if(!this.parseBeanFromRequest(bean, request)){
			return this.getErr();
		}
		
		FupFolderDao dao=new FupFolderDao();
		String result=dao.deleteFolder(bean);
		if(result!=null){
			return new Err(result);
		}else{
			return new Ok("文件夹删除成功");
		}
	}
       

}
