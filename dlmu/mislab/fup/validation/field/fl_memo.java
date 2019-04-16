// By GuRui on 2015-2-17 下午6:18:47
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fl_memo extends FieldString {

	public fl_memo(String val) {
		super(val);
		this.setMinLength(0);
		this.setMaxLength(500);
	}

}
