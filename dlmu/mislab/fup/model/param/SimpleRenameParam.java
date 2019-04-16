package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.IValidatable;
import dlmu.mislab.web.interact.IParameter;

public class SimpleRenameParam implements IParameter, IValidatable{
	private Long fl_no;
	private String fl_name;

	public Long getFl_no() {
		return fl_no;
	}

	public void setFl_no(Long fl_no) {
		this.fl_no = fl_no;
	}

	public String getFl_name() {
		return fl_name;
	}

	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}

}
