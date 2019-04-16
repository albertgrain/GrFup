// By GuRui on 2017-7-11 上午10:51:57
package dlmu.mislab.fup.validation.field;

import java.util.Date;

import dlmu.mislab.tool.DateTool;
import dlmu.mislab.validation.FieldDate;

public class qry_start extends FieldDate {

	public qry_start(Date val) {
		super(val);
		this.setMin(DateTool.createDate(1999,12,31,23,59,59));
		this.setMax(DateTool.createDate(2051,1,1,0,0,0));
	}


}
