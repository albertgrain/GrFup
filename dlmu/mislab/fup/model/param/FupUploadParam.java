// By GuRui on 2015-2-17 下午12:01:05
package dlmu.mislab.fup.model.param;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.validation.NoValidation;
import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IParameter;


public class FupUploadParam implements IParameter{
	@NotNull
	private Integer fd_no;
	private String show_type=FupDict.FUP_DOCUMENT_TYPE_LINK;
	
	@NoValidation
	private String owner_id; //Assigned UserInfo.UserId from Session in FupUploadServlet

	private String extra;
	
	@Override
	public String toString(){
		return "fd_no:"+this.fd_no+ " owner_id:"+this.owner_id +" show_type:"+this.show_type;
	}
	public void setFd_no(String sFdNo) {
		try{
			this.fd_no=Integer.parseInt(sFdNo);
		}catch(Exception e){}
	}
	
	public Integer getFd_no() {
		return fd_no;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
}
