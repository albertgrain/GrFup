package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldInt;

public class fno extends FieldInt {

	public fno(Integer val) {
		super(val);
		this.setMin(1);
	}

}
