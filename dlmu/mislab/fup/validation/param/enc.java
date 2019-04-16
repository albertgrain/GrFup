// By GuRui on 2015-4-18 下午2:41:47
package dlmu.mislab.fup.validation.param;

import dlmu.mislab.validation.FieldString;

/***
 * Validation class for download parameter: filename encoding
 * By GuRui on 2015-4-18 下午2:41:50
 *
 */
public class enc extends FieldString {

	public enc(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(1);
	}

}
