// By GuRui on 2015-2-17 下午6:18:09
package dlmu.mislab.fup.validation.field;

import dlmu.mislab.common.LogicError;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.validation.FieldString;
import dlmu.mislab.validation.ValidationError;

public class show_type extends FieldString {
	private static final String SHOW_TYPE_LINK=FupDict.FUP_DOCUMENT_TYPE_IMAGE;
	private static final String SHOW_TYPE_IMG=FupDict.FUP_DOCUMENT_TYPE_IMAGE;
	@Override
	public LogicError validate() {
		LogicError rtn=super.validate();
		if(rtn!=null){
			if(!SHOW_TYPE_LINK.equals(this.val) && !SHOW_TYPE_IMG.equals(this.val)){
				rtn= new ValidationError("show_type",FupDict.dict.get(FupDict.TAG_SHOW_TYPE)+"不正确");
			}
		}
		return rtn;
	}

	public show_type(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(4);
	}

}
