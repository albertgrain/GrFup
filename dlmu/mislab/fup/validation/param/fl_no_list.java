// By GuRui on 2015-4-19 上午9:37:11
package dlmu.mislab.fup.validation.param;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dlmu.mislab.common.LogicError;
import dlmu.mislab.validation.FieldString;
import dlmu.mislab.validation.ValidationError;

/**
 * Validation class for delete files/copy files: file no list to delete seperate by comma.
 * By GuRui on 2015-4-19 上午9:37:25
 *
 */
public class fl_no_list extends FieldString {
	private static final String PATTERN_STRING="^(\\d{1,10}\\,){0,99}\\d{1,10}$"; //20 /  212,1213
	private static final Pattern pattern = Pattern.compile(PATTERN_STRING);
	
	public fl_no_list(String val) {
		super(val);
		this.setMinLength(1);
		this.setMaxLength(1100); //100 largest int with comma
	}

	@Override
	public LogicError validate() {
		LogicError rtn= super.validate();
		if(rtn==null){
			Matcher m = pattern.matcher(this.getVal());
			if(m.find()){
				return null;
			}else{
				return new LogicError(ValidationError.CODE_BIZ_LOGIC_ERROR,"文件号列表格式不对，应为逗号分隔的数字，且不超过100个");
			}
		}
		return rtn;
	}
	
}
