// By GuRui on 2015-2-16 下午2:24:59
package dlmu.mislab.fup.model;

import java.util.Date;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.orm.IOrmBean;
import dlmu.mislab.orm.annotation.IsKey;
import dlmu.mislab.orm.annotation.Table;
import dlmu.mislab.web.interact.IParameter;
import dlmu.mislab.web.interact.IResponse;

@Table(Name=FupDict.FUP_TABLE_FOLDER)
public class FupFolderModel implements IResponse,IParameter, IOrmBean {
	@IsKey
	private Integer fd_no;
	private Integer parent_fd_no;
	private String fd_name;
	private String fd_name_local;
	private Date create_date;
	private String owner_id;
	private String fd_memo;
	
	public Integer getFd_no() {
		return this.fd_no;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
	public String getFd_name() {
		return this.fd_name;
	}
	public void setFd_name(String fd_name) {
		this.fd_name = fd_name;
	}
	public String getFd_name_local() {
		return this.fd_name_local;
	}
	public void setFd_name_local(String fd_name_local) {
		this.fd_name_local = fd_name_local;
	}
	public String getOwner_id() {
		return this.owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getFd_memo() {
		return fd_memo;
	}
	public void setFd_memo(String fd_memo) {
		this.fd_memo = fd_memo;
	}
	public Integer getParent_fd_no() {
		return parent_fd_no;
	}
	public void setParent_fd_no(Integer parent_fd_no) {
		this.parent_fd_no = parent_fd_no;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
}
