// By GuRui on 2017-7-11 上午10:48:08
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class qry_fl_name extends FieldString {

	public qry_fl_name(String val) {
		super(val);
		this.setMaxLength(250);
	}
}
