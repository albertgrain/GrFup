package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.IValidatable;
import dlmu.mislab.web.interact.IParameter;

public class SimpleGetPubMultiParam implements IParameter, IValidatable{
	private String[] fullnames;

	public String[] getFullnames() {
		return fullnames;
	}

	public void setFullnames(String[] fullnames) {
		this.fullnames = fullnames;
	}

	
}
