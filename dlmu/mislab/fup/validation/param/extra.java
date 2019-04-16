// By GuRui on 2015-12-9 上午4:14:41
package dlmu.mislab.fup.validation.param;

import dlmu.mislab.validation.FieldString;

public class extra extends FieldString {

	public extra(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(500);
	}

}
