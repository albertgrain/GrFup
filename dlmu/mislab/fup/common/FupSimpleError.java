package dlmu.mislab.fup.common;

import dlmu.mislab.common.LogicError;

public class FupSimpleError extends LogicError {
	private static final long serialVersionUID = 1L;

	private FupSimpleError(int errCode, String errMsg) {
		super(errCode, errMsg);
	
	}
	
	//validation
	public static final FupSimpleError FUP_SIMPLE_WRONG_FILENO = new FupSimpleError(6001, "文件编号不正确");
	public static final FupSimpleError FUP_SIMPLE_FILENO_BELOW_ZERO = new FupSimpleError(6002, "文件编号应大于0");
	public static final FupSimpleError FUP_SIMPLE_FILENAME_EMPTY = new FupSimpleError(6010, "文件名为空");
	
	public static final FupSimpleError FUP_SIMPLE_NULL_USERID = new FupSimpleError(6010, "用户ID为空");
	public static final FupSimpleError FUP_SIMPLE_NULL_ROLE = new FupSimpleError(6010, "用户角色为空");
	
	public static final FupSimpleError FUP_SIMPLE_ADD_TO_PUB_NOT_ALLOWED = new FupSimpleError(6010, "普通用户无权向公有目录上传文件");
	
	//read db
	public static final FupSimpleError FUP_SIMPLE_QUERY_FAILED= new FupSimpleError(6100, "查询失败");
	public static final FupSimpleError FUP_SIMPLE_FILE_NOT_FOUND= new FupSimpleError(6101, "没有找到对应文件的详细信息");
	
	//write db
	public static final FupSimpleError FUP_SIMPLE_GEN_FILENO_FAILED= new FupSimpleError(6200, "从数据库中未能得到并生成文件编号");
	public static final FupSimpleError FUP_SIMPLE_LAST_DB_UPDATE_FAILED= new FupSimpleError(6210, "新增完成后更新本地路径信息失败，新增操作可能已经完成，但无法正常使用。");
	public static final FupSimpleError FUP_SIMPLE_DB_RENAME_FAILED= new FupSimpleError(6211, "数据库重命名失敗");
	
	public static final FupSimpleError FUP_SIMPLE_DELETE_DB_FAILED= new FupSimpleError(6220, "从数据库删除文件失败");
	public static final FupSimpleError FUP_SIMPLE_DELETE_NOT_OWNER= new FupSimpleError(6221, "必须是文件所有者或管理员用户才有权删除文件");
	public static final FupSimpleError FUP_SIMPLE_FILE_LOCKED= new FupSimpleError(6222, "文件已锁定");
		
	//read disk
	public static final FupSimpleError FUP_SIMPLE_FILE_READ_FAILED= new FupSimpleError(6301, "读取文件失败");
	
	//write disk
	public static final FupSimpleError FUP_SIMPLE_FILE_WRITE_FAILED= new FupSimpleError(6400, "文件写入磁盘失败");
	public static final FupSimpleError FUP_SIMPLE_FILE_DELETE_FAILED= new FupSimpleError(6410, "从数据库删除文件成功，但从磁盘物理删除失败。删除完成（但系统可能存在垃圾数据）");
	
	
	

}
