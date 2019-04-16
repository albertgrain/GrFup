// By GuRui on 2015-4-19 下午7:19:31
package dlmu.mislab.fup.model.response;

import dlmu.mislab.orm.IOrmBean;
import dlmu.mislab.web.interact.IResponse;


/**
 * Used for Delete, return file to delete but link is found
 * By GuRui on 2015-4-20 上午9:21:03
 *
 */
public class FupFileRspn implements IResponse,IOrmBean {
	private Integer fl_no;
	private String fl_name;
	private Integer fd_no;
	private String fd_name;
//	private String owner_id;
//	private String fl_name_local;
//	private Integer fl_no_link;
//	
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
	public Integer getFd_no() {
		return fd_no;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
	public String getFd_name() {
		return fd_name;
	}
	public void setFd_name(String fd_name) {
		this.fd_name = fd_name;
	}
}
