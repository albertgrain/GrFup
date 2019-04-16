// By GuRui on 2015-4-24 下午1:42:03
package dlmu.mislab.fup.model.param;

import dlmu.mislab.web.interact.IParameter;


public class FupCopyFileParam implements IParameter {
	private Integer f_fd_no; //from 
	private Integer t_fd_no; //to
	private String fl_no_list;
	public Integer getF_fd_no() {
		return f_fd_no;
	}
	public void setF_fd_no(Integer f_fd_no) {
		this.f_fd_no = f_fd_no;
	}
	public Integer getT_fd_no() {
		return t_fd_no;
	}
	public void setT_fd_no(Integer t_fd_no) {
		this.t_fd_no = t_fd_no;
	}
	public String getFl_no_list() {
		return fl_no_list;
	}
	public void setFl_no_list(String fl_no_list) {
		this.fl_no_list = fl_no_list;
	}
}
