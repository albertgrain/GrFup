// By GuRui on 2015-2-18 上午3:07:26
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldInt;

public class parent_fd_no extends FieldInt {

	public parent_fd_no(Integer val) {
		super(val);
		this.setMin(1);
	}

}
