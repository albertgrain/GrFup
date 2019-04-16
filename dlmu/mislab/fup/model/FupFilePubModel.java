package dlmu.mislab.fup.model;

import dlmu.mislab.web.interact.IParameter;
import dlmu.mislab.web.interact.IResponse;

public class FupFilePubModel implements IResponse, IParameter {
	private String fl_name;

	public String getFl_name() {
		return fl_name;
	}

	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}

}
