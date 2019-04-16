package dlmu.mislab.fup.model;

import dlmu.mislab.web.interact.IParameter;


public class FileNameBean implements IParameter{
	private String fullname;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}