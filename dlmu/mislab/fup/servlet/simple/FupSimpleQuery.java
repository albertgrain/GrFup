package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.OkList;
import dlmu.mislab.web.servlet.SessionJsonBase;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.SimpleFileBean;
import dlmu.mislab.fup.model.param.SimpleQueryParam;

/***
 * 根据条件查询文件信息。此服务并不下载文件。可以根据此服务获得的fl_no后续逐个或成批下载文件
 * 返回：满足条件的文件信息列表
 */
@WebServlet(FupDict.FUP_SIMPLE_QUERY)
public class FupSimpleQuery extends SessionJsonBase {
	private static final long serialVersionUID = 1L;
	
	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleQueryParam bean=new SimpleQueryParam();
		if(!this.parseBeanFromRequestAndValidate(bean, request, FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		
		List<SimpleFileBean> rtn =SimpleQuery.query(bean);
		if(rtn!=null){
			return new OkList<SimpleFileBean>(rtn);
		}
		return new Err(FupSimpleError.FUP_SIMPLE_QUERY_FAILED);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}
}
