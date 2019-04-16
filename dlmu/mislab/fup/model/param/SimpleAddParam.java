package dlmu.mislab.fup.model.param;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.validation.IValidatable;
import dlmu.mislab.validation.NoValidation;
import dlmu.mislab.web.interact.IParameter;

public class SimpleAddParam implements IParameter, IValidatable{
	private String fullname;
	
	@NoValidation
	private String data;		// data:image/jpeg;base64,R0lGODlhuAE6AfYAAAQF...== 
	
	private String extra;
	private String show_type=FupDict.FUP_DOCUMENT_TYPE_IMAGE; //show_type = img
	private String fl_memo;
	
	private Boolean is_private = false; //If true save file to private folder(user_id named folder), else save to pub folder
	
	private Integer fd_no = FupDict.FUP_MY_FOLDER_NO;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getExtra() {
		return extra;
	}
	public void setExtra(String extra) {
		this.extra = extra;
	}
	public String getShow_type() {
		return show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	public String getFl_memo() {
		return fl_memo;
	}
	public void setFl_memo(String fl_memo) {
		this.fl_memo = fl_memo;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Boolean getIs_private() {
		return is_private;
	}
	public void setIs_private(Boolean is_private) {
		this.is_private = is_private;
	}
	public Integer getFd_no() {
		return fd_no;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}

}
