package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.SimpleFileBean;

/***
 * 根据条件查询文件信息。此服务并不下载文件。可以根据此服务获得的fl_no后续逐个或成批下载文件
 * 返回：满足条件的文件信息列表
 */
@WebServlet(FupDict.FUP_SIMPLE_QUERY_ONE)
public class FupSimpleQueryOne extends SessionJsonBase {
	private static final long serialVersionUID = 1L;
	
	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String sFlNo=request.getParameter(FupDict.TAG_FL_NO);
		Long flNo=null;
		try{
			flNo = Long.parseLong(sFlNo);
		}catch(Exception e){
			return new Err(FupSimpleError.FUP_SIMPLE_WRONG_FILENO);
		}
		if(flNo<=0){
			return new Err(FupSimpleError.FUP_SIMPLE_FILENO_BELOW_ZERO);
		}
		SimpleFileBean rtn = SimpleQuery.queryOne(flNo);
		if(rtn==null){
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_NOT_FOUND);
		}
		return new Ok(rtn);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}
}
