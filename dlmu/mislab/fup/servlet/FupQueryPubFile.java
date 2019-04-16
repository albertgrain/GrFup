package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.FileExistBean;
import dlmu.mislab.fup.model.FileNameListBean;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.OkList;
import dlmu.mislab.web.servlet.JsonBase;

/**
 * Query if file or files exist on the server's pub directory
 * Parameter : fl_name_list=123.jpg,345.jpg,789.jpg
 */
@WebServlet(FupDict.FUP_ACTION_PUBLIC_FILE_QUERY)
public class FupQueryPubFile extends JsonBase{
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FileNameListBean bean =new FileNameListBean();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		String[] fls= bean.getFl_name_list().split(",");
		List<FileExistBean> rtn=new LinkedList<FileExistBean>();

		for(String fn:fls){
			File f= new File(FupDict.FUP_PHYSICAL_ROOT+ FupDict.FUP_DEFAULT_PUBLIC_PATH + ConfigBase.PATH_DELIMETER +fn);
			FileExistBean e=new FileExistBean();
			e.setExist(f.exists());
			e.setFl_name(fn);
			rtn.add(e);
		}
		return new OkList<FileExistBean>(rtn);
	}
	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}

}
