package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class fl_name_list extends FieldString {

	public fl_name_list(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(2500);
	}


}
