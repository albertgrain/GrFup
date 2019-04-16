package dlmu.mislab.fup.model.param;

import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.web.interact.IParameter;

public class FupDownloadPublicParam implements IParameter{

	private Boolean att=false; //是否以附件形式下载文件
	private String enc=AttchementFileNameEncoder.ENCODING_DEFAULT; //文件名的编码方式
	private String fullname=FupDict.FUP_DEFAULT_FILE_NOT_FOUND_ICON; //文件名（带扩展名）
	private Boolean is_big=false; // Is image larger than 500K

	public Boolean getAtt() {
		return att;
	}
	public void setAtt(Boolean att) {
		this.att = att;
	}
	public String getEnc() {
		return enc;
	}
	public void setEnc(String enc) {
		this.enc = enc;
	}

	public Boolean getIs_big() {
		return is_big;
	}
	public void setIs_big(Boolean is_big) {
		this.is_big = is_big;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}


}
