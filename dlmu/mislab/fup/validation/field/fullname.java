// By GuRui on 2017-7-14 上午9:11:45
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fullname extends FieldString {

	public fullname(String val) {
		super(val);
		this.setMaxLength(259);
		this.setMinLength(3);
	}

}
