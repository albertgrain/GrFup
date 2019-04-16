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
 * Servlet implementation class FupRenameFolder
 */
@WebServlet(FupDict.FUP_ACTION_RENAME_FOLDER)
public class FupRenameFolder extends SessionJsonBase{
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
		boolean result=dao.renameFolder(bean);
		if(result){
			return new Ok("文件夹重命名成功");			
		}else{
			return new Err("重命名文件夹失败。可能原因：1.是否有权限  2.其父文件夹是否存在 3.没有找到文件夹");			
		}
	}
	

}
