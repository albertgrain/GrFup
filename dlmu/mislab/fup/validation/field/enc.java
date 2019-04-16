package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldString;

public class enc extends FieldString{

	public enc(String val) {
		super(val);
		this.setMaxLength(1);
		this.setMinLength(0);
	}


}
