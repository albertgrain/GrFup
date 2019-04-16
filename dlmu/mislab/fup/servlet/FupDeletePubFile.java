package dlmu.mislab.fup.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.FileNameBean;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;
import dlmu.mislab.web.servlet.SessionJsonBase;


/**
 * Servlet implementation class FupDeletePubFile
 */
@WebServlet(FupDict.FUP_ACTION_DELETE_PUBLIC_FILE)
public class FupDeletePubFile extends SessionJsonBase {
	private static final long serialVersionUID = 1L;

	@Override
	public IResponse doJsonGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		return Err.METHOD_GET_NOT_SUPPORTED;
	}

	@Override
	public IResponse doJsonPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(!FupDict.PUBLIC_UPLOAD_ROLE.equals(this.getUserInfo().getRole())){
			return new Err("用户类型:"+this.getUserInfo().getRole()+"没有删除公共图片的权限!");
		}
		FileNameBean bean=new FileNameBean();
		if(!this.parseBeanFromRequestAndValidate(bean, request,FupDict.FUP_VALIDATION_PACKAGE_NAME)){
			return this.getErr();
		}
		if(FupDict.FUP_DEFAULT_FILE_NOT_FOUND_ICON.equals(bean.getFullname())){
			return new Err("此文件不可删除");
		}
		String fullpath=FupDict.FUP_PHYSICAL_ROOT+FupDict.FUP_DEFAULT_PUBLIC_PATH + ConfigBase.PATH_DELIMETER + bean.getFullname();
		File file=new File(fullpath);
		if(!file.exists()){
			return new Err("待删除文件不存在");
		}else{
			try{
				file.delete();
			}catch(Exception e){
				return new Err("删除失败，请检查文件访问权限");
			}
		}
		return new Ok(bean.getFullname());
	}

}
