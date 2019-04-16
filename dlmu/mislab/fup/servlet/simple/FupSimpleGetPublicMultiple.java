package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.param.SimpleGetPubMultiParam;
import dlmu.mislab.tool.JsonParseBadFieldException;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.interact.SimpleIJson;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.JsonFieldParseError;
import dlmu.mislab.web.response.OkList;
import dlmu.mislab.web.servlet.JsonBase;
import dlmu.mislab.web.servlet.JsonTool;

/***
 * 需要参数：data：包含两个等长数组fl_nos和fl_exts
 * 每个数组对应位置包含fl_no：文件编号 和  fl_ext：文件扩展名（不含。）
 * 返回一个Ok对象，其中额rspn.msg包含了Base64编码的文件内容
 */
@WebServlet(FupDict.FUP_SIMPLE_GET_PUBLIC_MULTIPLE)
public class FupSimpleGetPublicMultiple extends JsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		SimpleGetPubMultiParam bean = null;
		try{
			bean =JsonTool.getData(request, SimpleGetPubMultiParam.class);
		}catch(JsonParseBadFieldException e){
			return new JsonFieldParseError(e.getBadFieldName(), FupDict.dict.get(e.getBadFieldName()));
		}
		if(bean==null || bean.getFullnames()==null || bean.getFullnames().length==0){
			return new Err(FupSimpleError.FUP_SIMPLE_FILENAME_EMPTY);
		}
				
		List<SimpleIJson> rspns=new ArrayList<SimpleIJson>(bean.getFullnames().length);
		for(String fl : bean.getFullnames()){
			String flStr = FupSimpleGetPublic.loadFileToBase64String(fl, this.logger);
			if(flStr==null){
				return new Err(FupSimpleError.FUP_SIMPLE_FILE_READ_FAILED);
			}
			rspns.add(new SimpleIJson(flStr));
		}
		return new OkList<SimpleIJson>(rspns);
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_POST_NOT_SUPPORTED;
	}
}
