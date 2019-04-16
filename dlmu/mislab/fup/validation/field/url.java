// By GuRui on 2015-2-17 下午6:17:10
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class url extends FieldString {

	public url(String val) {
		super(val);
		this.setMinLength(3);
		this.setMaxLength(250);
	}

}
