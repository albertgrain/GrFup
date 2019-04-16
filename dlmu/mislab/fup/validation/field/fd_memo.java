// By GuRui on 2015-2-17 下午6:22:50
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fd_memo extends FieldString {

	public fd_memo(String val) {
		super(val);
		this.setMinLength(0);
		this.setMaxLength(500);
	}

}
