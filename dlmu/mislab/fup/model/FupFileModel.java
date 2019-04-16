// By GuRui on 2015-2-16 下午2:22:48
package dlmu.mislab.fup.model;

import java.util.Date;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.IFile;
import dlmu.mislab.orm.IOrmBean;
import dlmu.mislab.orm.annotation.IsAuto;
import dlmu.mislab.orm.annotation.IsKey;
import dlmu.mislab.orm.annotation.Table;
import dlmu.mislab.validation.NoValidation;
import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IResponse;

@Table(Name=FupDict.FUP_TABLE_FILE)
public class FupFileModel implements IResponse,IFile, IOrmBean{
	@IsAuto
	@NoValidation
	@IsKey
	private Integer fl_no; //Auto generated
	
	@dlmu.mislab.validation.NotNull
	private Integer fd_no=0; //Default is 0, means upload to one's private folder. If >0, then it is a virtual folder.
	
	@NoValidation
	private Integer fl_no_link=0; //Default is 0, means this is the physical file, not a link.
	
	@NotNull
	private String owner_id;
	
	private String fl_name;
	@NotNull
	private String fl_name_local;
	@NotNull
	private String fl_ext="unknown";
	
	@NoValidation
	private Long fl_size;
	
	@NoValidation
	private Date upload_date;
	
	private String url;
	
	private String show_type=FupDict.FUP_DOCUMENT_TYPE_LINK;
	
	private String fl_memo;
	
	private Boolean locked = false;
	
	public FupFileModel copy(){
		FupFileModel rtn=new FupFileModel();
		rtn.fl_no=this.fl_no;
		rtn.fd_no=this.fd_no;
		rtn.fl_no_link=this.fl_no_link;
		rtn.owner_id=this.owner_id;
		rtn.fl_name=this.fl_name;
		rtn.fl_name_local=this.fl_name_local;
		rtn.fl_ext=this.fl_ext;
		rtn.fl_size=this.fl_size;
		rtn.upload_date=this.upload_date;
		rtn.url=this.url;
		rtn.show_type=this.show_type;
		rtn.fl_memo=this.fl_memo;
		return rtn;
	}
	
	public Integer getFl_no() {
		return this.fl_no;
	}
	public void setFl_no(Integer fl_no) {
		this.fl_no = fl_no;
	}
	public Integer getFd_no() {
		return this.fd_no;
	}
	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
	public String getFl_name() {
		return this.fl_name;
	}
	public void setFl_name(String fl_name) {
		this.fl_name = fl_name;
	}
	public String getFl_name_local() {
		return this.fl_name_local;
	}
	public void setFl_name_local(String fl_name_local) {
		this.fl_name_local = fl_name_local;
	}
	public String getFl_ext() {
		return this.fl_ext;
	}
	public void setFl_ext(String fl_ext) {
		this.fl_ext = fl_ext;
	}
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShow_type() {
		return this.show_type;
	}
	public void setShow_type(String show_type) {
		this.show_type = show_type;
	}
	public String getFl_memo() {
		return this.fl_memo;
	}
	public void setFl_memo(String fl_memo) {
		this.fl_memo = fl_memo;
	}
	public Date getUpload_date() {
		return this.upload_date;
	}
	public void setUpload_date(Date upload_date) {
		this.upload_date = upload_date;
	}
	public Long getFl_size() {
		return fl_size;
	}
	public void setFl_size(Long fl_size) {
		this.fl_size = fl_size;
	}
	public String getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}
	public Integer getFl_no_link() {
		return fl_no_link;
	}
	public void setFl_no_link(Integer fl_no_link) {
		this.fl_no_link = fl_no_link;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
}
