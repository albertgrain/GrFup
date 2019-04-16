// By GuRui on 2015-2-17 下午6:20:24
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fd_name extends FieldString {

	public fd_name(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(250);
	}

}
