package dlmu.mislab.fup.model;

import dlmu.mislab.web.interact.IResponse;


public class FileExistBean implements IResponse{

	private String fl_name;
	private Boolean exist=false;
	public String getFl_name() {
		return fl_name;
	}
	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}
	public Boolean getExist() {
		return exist;
	}
	public void setExist(Boolean exist) {
		this.exist = exist;
	}


}
