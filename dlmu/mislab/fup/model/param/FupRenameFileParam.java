// By GuRui on 2015-4-24 上午8:34:19
package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IParameter;

public class FupRenameFileParam implements IParameter {
	
	@NotNull
	private Integer fl_no;
	
	@NotNull
	private String fl_name;
		
	public Integer getFl_no() {
		return fl_no;
	}
	public void setFl_no(Integer fl_no) {
		this.fl_no = fl_no;
	}
	public String getFl_name() {
		return fl_name;
	}
	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}
}
