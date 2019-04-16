package dlmu.mislab.fup.model;

import java.util.Date;

import dlmu.mislab.orm.IOrmBean;
import dlmu.mislab.orm.annotation.IsKey;
import dlmu.mislab.orm.annotation.Table;
import dlmu.mislab.validation.NoValidation;
import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.fup.FupDict;

@Table(Name=FupDict.FUP_TABLE_FILE)
public class SimpleFileBean  implements IOrmBean, IResponse{
	@IsKey
	private Long fl_no;
	
	private String fl_name;
	private String fl_ext;
	@NoValidation
	private String owner_id= FupDict.SIMPLE_FOLDER_NAME; 	//owner_id默认为共享文件,否则为/my/owner_id/ 目录
	@NotNull
	private String fl_name_local;
	private Integer fl_size;
	private Integer fd_no=FupDict.FUP_MY_FOLDER_NO;			//所有文件位于我的文件夹
	
	private final Date upload_date= new Date();
	
	private String mime;		// data:image/jpeg;base64,...
	
	private String show_type;
	private String fl_memo;
	
	private Boolean locked;
	
	transient
	private String enc=AttchementFileNameEncoder.ENCODING_DEFAULT;
	
	public String getFl_name() {
		return fl_name;
	}
	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}
	public String getFl_ext() {
		return fl_ext;
	}
	public void setFl_ext(String fl_ext) {
		this.fl_ext = fl_ext;
	}
	public String getFl_name_local() {
		return fl_name_local;
	}
	public void setFl_name_local(String fl_name_local) {
		this.fl_name_local = fl_name_local;
	}
	public Integer getFl_size() {
		return fl_size;
	}
	public void setFl_size(Integer fl_size) {
		this.fl_size = fl_size;
	}
	public Integer getFd_no() {
		return fd_no;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public Date getUpload_date() {
		return upload_date;
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
	public Long getFl_no() {
		return fl_no;
	}
	public void setFl_no(Long fl_no) {
		this.fl_no = fl_no;
	}
	public String getEnc() {
		return enc;
	}
	public void setEnc(String enc) {
		this.enc = enc;
	}
	public String getMime() {
		return mime;
	}
	public void setMime(String mime) {
		this.mime = mime;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	
}
