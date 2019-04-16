package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.IValidatable;
import dlmu.mislab.web.interact.IParameter;

public class SimpleRemoveParam implements IParameter, IValidatable{
	private Long fl_no;

	public Long getFl_no() {
		return fl_no;
	}

	public void setFl_no(Long fl_no) {
		this.fl_no = fl_no;
	}

}
