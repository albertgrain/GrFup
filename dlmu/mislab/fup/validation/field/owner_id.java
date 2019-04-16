// By GuRui on 2015-2-17 下午6:22:12
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class owner_id extends FieldString {

	public owner_id(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(50);
	}

}
