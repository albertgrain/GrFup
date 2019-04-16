// By GuRui on 2015-2-17 下午6:14:18
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.validation.FieldLong;

public class fl_size extends FieldLong {

	public fl_size(Long val) {
		super(val);
		this.setMin(0);
	}

}
