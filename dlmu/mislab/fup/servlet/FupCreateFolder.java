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
 * Servlet implementation class FupCreateFolder
 */
@WebServlet(FupDict.FUP_ACTION_CREATE_FOLDER)
public class FupCreateFolder extends SessionJsonBase {
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
		bean.setOwner_id(this.getUserInfo().getUserId());
		FupFolderDao dao=new FupFolderDao();
		int result=dao.createFolder(bean);
		if(result<0){
			return new Err("创建新文件夹失败。请检查:1.是否重名 2.是否有权限 3.其父文件夹是否存在");
		}
		return new Ok("新文件夹创建成功");
	}
}
