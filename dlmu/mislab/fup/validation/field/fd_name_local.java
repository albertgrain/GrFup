// By GuRui on 2015-2-17 下午6:21:28
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fd_name_local extends FieldString {

	public fd_name_local(String val) {
		super(val);
		this.setMinLength(3);
		this.setMaxLength(250);
	}

}
