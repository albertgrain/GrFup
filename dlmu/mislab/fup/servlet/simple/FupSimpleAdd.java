package dlmu.mislab.fup.servlet.simple;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.servlet.SessionJsonBase;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.param.SimpleAddParam;

/***
 * 提交一个文件。需要的参数包括
 * 必需参数：fl_name:文件名（不含扩展名） fl_ext:扩展名（不含.） data:经过Base64编码的数据
 * 可选参数： extra:附加控制命令（暂未生效） show_type:显示类型（img或link，默认img） fl_memo:备注信息, is_private:是否保存到共有目录
 * 返回：新增加的文件编号
 */
@WebServlet(FupDict.FUP_SIMPLE_ADD)
public class FupSimpleAdd extends SessionJsonBase {
	private static final long serialVersionUID = 1L;
		
	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleAddParam bean = new SimpleAddParam();
		
		if(!this.parseBeanFromRequestAndValidate(bean, request,FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		
		return SimpleAdd.addFile(bean, this.getUserInfo().getUserId(), this.getUserInfo().getRole());
	}
}
