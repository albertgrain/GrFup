// By GuRui on 2015-2-18 上午2:09:04
package dlmu.mislab.fup.model.param;

import dlmu.mislab.web.interact.IParameter;

public class FupDeleteFileParam implements IParameter{
	/***
	 * fl_no is a string of fl_nos separated by comma
	 */
	private String fl_no_list;
	
	private String extra;

	public String getFl_no_list() {
		return fl_no_list;
	}

	public void setFl_no_list(String fl_no_list) {
		this.fl_no_list = fl_no_list;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
