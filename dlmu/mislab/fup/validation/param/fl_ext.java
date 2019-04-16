// By GuRui on 2015-2-17 下午6:10:10
package dlmu.mislab.fup.validation.param;

import dlmu.mislab.validation.FieldString;

public class fl_ext extends FieldString {

	public fl_ext(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(10);
	}

}
