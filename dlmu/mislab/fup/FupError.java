// By GuRui on 2015-2-17 上午9:26:49
package dlmu.mislab.fup;

import dlmu.mislab.common.LogicError;


public class FupError extends LogicError{
	private static final long serialVersionUID = 1L;

	public static final int CODE_FILE_UPLOAD_ERROR=10000;
	
	public static final LogicError FUP_GENERAL_ERROR=new LogicError(10000,"上传一般性错误");
	public static final LogicError FUP_NO_FILE=new LogicError(10001,"没有发现上传文件");
	public static final LogicError FUP_SAVE_TO_DB_FAILED=new LogicError(10002,"保存文件信息失败");
	public static final LogicError FUP_INVALID_PARAMETERS=new LogicError(10003,"上传所需参数错误");
	public static final LogicError FUP_READ_HTTP_FAILED=new LogicError(10004,"解析上传HTTP信息失败");
	public static final LogicError FUP_CREATE_PATH_FAILED=new LogicError(10005,"创建上传路径失败");
	public static final LogicError FUP_WRITE_FILE_FAILED=new LogicError(10006,"向磁盘写入文件失败");
	public static final LogicError FUP_DELETE_FROM_DATABASE_FAILED=new LogicError(10007,"从数据库删除文件信息失败");
	public static final LogicError FUP_UNKNOWN_UPLOAD_TYPE=new LogicError(10008,"未知上传类型");
	public static final LogicError FUP_BAD_ADDRESS=new LogicError(10009,"错误的地址");
	public static final LogicError FUP_FILE_NAME_ENCODING_NOT_SUPPORTED=new LogicError(10010,"文件名编码方式不是utf-8");
	public static final LogicError FUP_PARTIAL_SUCCESS=new LogicError(10011,"仅有部分文件上传成功，请刷新页面");
	public static final LogicError FUP_PARSE_TO_DELETE_FILE_NOS_ERROR=new LogicError(10011,"解析待删除文件编码出错");
	public static final LogicError FUP_BEFORE_UPLOAD_CALLBACK_FAILED=new LogicError(20001,"文件上传预先处理失败");
	public static final LogicError FUP_AFTER_UPLOAD_CALLBACK_FAILED=new LogicError(20001,"文件上传成功但后续处理失败");
	public static final LogicError FUP_BEFORE_DELETE_CALLBACK_FAILED=new LogicError(20001,"文件删除预先处理失败");
	public static final LogicError FUP_AFTER_DELETE_CALLBACK_FAILED=new LogicError(20001,"文件删除成功但后续处理失败");
	
	private FupError(String errMsg) {
		super(CODE_FILE_UPLOAD_ERROR,errMsg);
	}

}
