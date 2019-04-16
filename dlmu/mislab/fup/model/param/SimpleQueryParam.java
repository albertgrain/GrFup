package dlmu.mislab.fup.model.param;

import java.util.Date;

import dlmu.mislab.tool.DateTool;
import dlmu.mislab.web.interact.IParameter;

public class SimpleQueryParam implements IParameter{
	private String qry_fl_name;
	private Date qry_start = DateTool.createDate(2000,1,1,0,0,0);
	private Date qry_end = DateTool.createDate(2050,12,31,23,59,59);

	public String getQry_fl_name() {
		return qry_fl_name;
	}
	public void setQry_fl_name(String qry_fl_name) {
		this.qry_fl_name = qry_fl_name;
	}
	public Date getQry_start() {
		return qry_start;
	}
	public void setQry_start(Date qry_start) {
		this.qry_start = qry_start;
	}
	public Date getQry_end() {
		return qry_end;
	}
	public void setQry_end(Date qry_end) {
		this.qry_end = qry_end;
	}
	
	

}
