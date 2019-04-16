// By GuRui on 2015-4-18 下午2:38:44
package dlmu.mislab.fup.validation.param;

import dlmu.mislab.validation.FieldInt;

public class fno extends FieldInt {

	public fno(Integer val) {
		super(val);
		this.setMin(0);
	}

}
