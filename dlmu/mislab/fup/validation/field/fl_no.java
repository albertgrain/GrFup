// By GuRui on 2015-2-17 下午6:07:04
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldLong;	
public class fl_no extends FieldLong {	
	public fl_no (Long fl_no) {	
		super(fl_no);	
		this.setMin(1L);	
	}	
}	
