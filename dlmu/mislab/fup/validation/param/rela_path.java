// By GuRui on 2015-8-27 下午5:40:49
package dlmu.mislab.fup.validation.param;

import dlmu.mislab.validation.FieldString;

public class rela_path extends FieldString {

	public rela_path(String val) {
		super(val);
		this.setMinLength(0);
		this.setMaxLength(500);
	}

}
