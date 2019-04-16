// By GuRui on 2015-8-27 下午5:39:13
package dlmu.mislab.fup.model.param;

import dlmu.mislab.fup.IFile;
import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IParameter;

public class FupFreeDownloadParam implements IParameter, IFile {
	@NotNull
	private String fl_name;
	
	private String fl_ext;
	
	/***
	 * 相对与ROOT/free/的相对路径
	 */
	private String rela_path=""; 
	
	public String getFl_name() {
		return fl_name;
	}
	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}
	
	public String getFl_ext() {
		return fl_ext;
	}
	public void setFl_ext(String fl_ext) {
		this.fl_ext = fl_ext;
	}
	public String getRela_path() {
		return rela_path;
	}
	public void setRela_path(String rela_path) {
		this.rela_path = rela_path;
	}


}
