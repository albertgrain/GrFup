// By GuRui on 2015-2-17 下午6:08:33
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fl_name extends FieldString {

	public fl_name(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(250);
	}

}
