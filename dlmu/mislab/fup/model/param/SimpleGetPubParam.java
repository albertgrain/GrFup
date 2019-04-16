package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.IValidatable;
import dlmu.mislab.web.interact.IParameter;

public class SimpleGetPubParam implements IParameter, IValidatable{
	private String fullname;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	

}
