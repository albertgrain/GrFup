// By GuRui on 2015-2-17 下午6:09:24
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fl_name_local extends FieldString {

	public fl_name_local(String val) {
		super(val);
		this.setMinLength(3);
		this.setMaxLength(250);
	}

}
