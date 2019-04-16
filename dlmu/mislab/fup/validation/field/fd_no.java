// By GuRui on 2015-2-17 下午6:19:56
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldInt;

public class fd_no extends FieldInt {

	public fd_no(Integer val) {
		super(val);
		this.setMin(0);
	}

}
