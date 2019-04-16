// By GuRui on 2015-4-18 下午2:11:41
package dlmu.mislab.fup.model.param;

import dlmu.mislab.fup.AttchementFileNameEncoder;
import dlmu.mislab.web.interact.IParameter;


public class FupDownloadParam implements IParameter{

	private Boolean att=false; //是否以附件形式下载文件
	private String enc=AttchementFileNameEncoder.ENCODING_DEFAULT; //文件名的编码方式
	private Integer fno=-1; //文件编号
	private Boolean is_big=false; //Is image size larger than 500K

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
	public Integer getFno() {
		return fno;
	}
	public void setFno(Integer fno) {
		this.fno = fno;
	}
	public Boolean getIs_big() {
		return is_big;
	}
	public void setIs_big(Boolean is_big) {
		this.is_big = is_big;
	}

}
