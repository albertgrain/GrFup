// By GuRui on 2015-2-18 上午2:15:10
package dlmu.mislab.fup;

import java.util.Hashtable;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.common.DictBase;


public class FupDict extends DictBase {
	public static IUploadFileCallBack CALLBACK_UPLOAD_FILE =null;
	public static IDeleteFileCallBack CALLBACK_DELETE_FILE = null;
	
	public static final String FUP_PUBLIC="pub";
	
	/***
	 * 受限上传文件的位置，默认为Web项目根的/home
	 * 受限上传文件是指不是存放于docshare目录、而直接存在于web目录中的文件。如，页面图片。
	 */
	public static final String FUP_RESTRICTED_UPLOAD_PATH="/home";
	
	/////////// FileUpload Simple ////////////////////
	public static final String FUP_RESTRICTED_SINGLETON_ADD = "/json/fup/x/add.queue";
	public static final String FUP_RESTRICTED_SINGLETON_DELETE = "/json/fup/x/del.queue";
	
	public static final String FUP_SIMPLE_GET_PUBLIC = "/json/fup/getpub";
	public static final String FUP_SIMPLE_GET_PUBLIC_MULTIPLE = "/json/fup/getpubs";
	public static final String FUP_SIMPLE_GET = "/json/fup/get";
	public static final String FUP_SIMPLE_REMOVE = "/json/fup/remove";
	public static final String FUP_SIMPLE_ADD = "/json/fup/add";
	public static final String FUP_SIMPLE_RENAME = "/json/fup/rename";
	public static final String FUP_SIMPLE_QUERY = "/json/fup/query";
	public static final String FUP_SIMPLE_QUERY_ONE = "/json/fup/queryone";
	
	public static final String SIMPLE_TAG_DATA="data";
	public static final String SIMPLE_TAG_FULL_NAME="fullname";
	public static final String SIMPLE_FOLDER_NAME=FUP_PUBLIC;
	public static final int FUP_SIMPLE_DOWN_FILE_CACHE_SIZE=64 * 1024; //64k
	
	/////////// FileUpload ////////////////////
	public static final String FUP_TAG_EXTRA_INFO="extra";

	public static final String FUP_KEY="fup_key";
	public static final String FUP_SEQUNCE_NO="fup_seq_no";
	public static final String FUP_IMG_SRC="fup_img_src";
	public static final String FUP_DOCUMENT_TYPE="fup_doc_type";
	public static final String FUP_NAME="fup_name";
	public static final String FUP_DOCUMENT_TYPE_IMAGE="img";
	public static final String FUP_DOCUMENT_TYPE_LINK="link";
	public static final String FUP_TARGET="fup_target";
	public static final String FUP_LIMIT_FUP_COUNT="fup_limit_fup_count";
	public static final String FUP_IMG_MAX_WIDTH_TAG="fup_max_width";
	public static final String FUP_IMG_MAX_HEIGHT_TAG="fup_max_height";
	public static final String FUP_READONLY_TAG="fup_ro";

	public static final String FUP_TAG_FOLDER_NO="fd_no";
	public static final String FUP_TAG_OWNER_ID="owner_id";
	public static final String FUP_TAG_SHOW_TYPE="show_type";
	public static final String FUP_TAG_FILE_NO="fl_no";
	public static final String FUP_TAG_FILE_NO_LIST="fl_no_list";
	public static final String FUP_TAG_COPY_FROM_FOLDER_NO="f_fd_no";
	public static final String FUP_TAG_COPY_TO_FOLDER_NO="t_fd_no";
	public static final String FUP_TAG_FORM_DATA="fup_formdata_name";
	public static final String FUP_TAG_SUCCESSFUL_CALLBACK="fup_successful_callback";
	public static final String FUP_TAG_UPLOADED_CALLBACK="func_uploaded";
	public static final String FUP_TAG_UPLOADED_PUBLIC_CALLBACK="func_pub_uploaded";
	public static final String FUP_TAG_DELETED_PUBLIC_CALLBACK="func_pub_deleted";

	public static final String FUP_TAG_NEW_FILE_NAMES="fup_new_names";

	public static final String FUP_DELIMETER_FILE_NO_LIST=",";

	public static final String FUP_TABLE_FILE="tb_fup_file";
	public static final String FUP_TABLE_FOLDER="tb_fup_folder";
	public static final String[] FUP_TABLE_FILE_KEYS=new String[]{"fl_no"};
	public static final String[] FUP_TABLE_FOLDER_KEYS=new String[]{"fd_no"};

	public static final String FUP_ACTION_DOWNLOAD_FILE="/fup/file/download";
	public static final String FUP_ACTION_DOWNLOAD_PUBLIC_FILE="/fup/file/"+FUP_PUBLIC;
	public static final String FUP_ACTION_PUBLIC_FILE_QUERY="/fup/file/query";
	public static final String FUP_ACTION_UPLOAD_FILE="/fup/file/upload";
	public static final String FUP_ACTION_DELETE_FILE="/fup/file/delete";
	public static final String FUP_ACTION_DELETE_PUBLIC_FILE="/fup/file/delpub";
	public static final String FUP_ACTION_RENAME_FILE="/fup/file/rename";
	public static final String FUP_ACTION_COPY_FILE="/fup/file/copy";
	public static final String FUP_ACTION_MOVE_FILE="/fup/file/move";
	public static final String FUP_ACTION_LIST_FILE="/fup/file/list";

	public static final String FUP_ACTION_FREE_UPLOAD="/fup/free/up";
	public static final String FUP_ACTION_UPLOAD_PUBLIC="/fup/"+FUP_PUBLIC+"/up";

	public static final String FUP_ACTION_CREATE_FOLDER="/fup/folder/new";
	public static final String FUP_ACTION_CREATE_PUBLIC_FOLDER="/fup/folder/newpub";
	public static final String FUP_ACTION_DELETE_FOLDER="/fup/folder/delete";
	public static final String FUP_ACTION_RENAME_FOLDER="/fup/folder/rename";
	public static final String FUP_ACTION_LIST_FOLDER="/fup/folder/list";

	public static final String FUP_DEFAULT_FILE_NOT_FOUND_ICON="nofile.png";
	public static final String FUP_DEFAULT_FREE_PATH=ConfigBase.PATH_DELIMETER+"free";
	public static final String FUP_DEFAULT_PUBLIC_PATH=ConfigBase.PATH_DELIMETER+FUP_PUBLIC;
	public static String FUP_FOLDER_ROOT_NAME=ConfigBase.DEFAULT_CONFIG_FOLDER;
	public static boolean FUP_NO_LINK_FILE=true; //Delete link files by force

	public static String FREE_UP_DOWN_ROLE="backup";
	public static String FUP_MANAGER_ROLE="man";
	public static String PUBLIC_UPLOAD_ROLE=FUP_MANAGER_ROLE; //只有管理员角色才可以上传公共图片

	public static final String FUP_CONFIG_FILE_EXT=".cfg";
	public static final String FUP_CONFIG_FOLDERNAME=ConfigBase.PATH_DELIMETER+ ConfigBase.DEFAULT_CONFIG_FOLDER + ConfigBase.PATH_DELIMETER +"conf";
	public static final String FUP_FOLDER_MY_NAME="my";
	public static final String FUP_FOLDER_SHARE_NAME="share";

	public static String FUP_PHYSICAL_ROOT=ConfigBase.PATH_DELIMETER+FUP_FOLDER_ROOT_NAME;
	public static String FUP_PHYSICAL_TEMP=FUP_PHYSICAL_ROOT+ConfigBase.PATH_DELIMETER+"temp";
	public static String FUP_PHYSICAL_MY=FUP_PHYSICAL_ROOT+ConfigBase.PATH_DELIMETER+FUP_FOLDER_MY_NAME;
	public static String FUP_VIRTUAL_SHARE=FUP_PHYSICAL_ROOT+ConfigBase.PATH_DELIMETER+FUP_FOLDER_SHARE_NAME;

	public static final int FUP_MY_FOLDER_NO=0;
	public static final String FUP_SYSTEM_USER_ID="0";
	public static final String FUP_USER_SHARE_OWNER_NO=null;
	public static final int FUP_SHARE_FOLDER_NO=1;
	public static final int FUP_DEFAULT_LINK_FILE_NO=0;
	public static final int FUP_MAX_FILE_SIZE=100000 * 1024; //100M
	public static final int FUP_MAX_MEM_SIZE=100000 * 1024;
	public static final int FUP_MAX_INLINE_IMG_SIZE=2000 * 1024; //2M
	public static final int FUP_DEFAULT_DOWN_FILE_CACHE_SIZE_BIG=1024 * 1024; //2m
	public static final int FUP_DEFAULT_DOWN_FILE_CACHE_SIZE_SMALL=256 * 1024; //2m

	public static final String TAG_IMG_MAX_WIDTH="fup_max_width";
	public static final String TAG_IMG_MAX_HEIGHT="fup_max_height";
	public static final int FUP_IMG_MAX_WIDTH=1920;
	public static final int FUP_IMG_MAX_HEIGHT=1024;

	public static final String FUP_VALIDATION_PACKAGE_NAME="dlmu.mislab.fup.validation.field";


	/*****************************  Fields *****************************/
	public static final String VALIDATOR_CONSTRUCTING_ERROR_MSG="验证输入字段构造验证对象时出错";
	public static final Hashtable<String,String> dict=new Hashtable<String,String>();



	/* Tag names for parameter English */
	public static final String TAG_DOWNLOAD_FILE_NO="fno";
	public static final String TAG_DOWNLOAD_AS_ATTACHMENT="att";
	public static final String TAG_DOWNLOAD_FILENAME_ENCODING_TYPE="enc";

	/* Tag names for parameter Chinese */
	public static final String TAG_DOWNLOAD_FILE_NO_CN="文件编号";
	public static final String TAG_DOWNLOAD_AS_ATTACHMENT_CN="是否下载为附件";
	public static final String TAG_DOWNLOAD_FILENAME_ENCODING_TYPE_CN="文件名编码方式";

	/* Field names English */
	public static final String TAG_CREATE_DATE="create_date";
	public static final String TAG_FD_MEMO="fd_memo";
	public static final String TAG_FD_NAME_LOCAL="fd_name_local";
	public static final String TAG_FD_NAME="fd_name";
	public static final String TAG_FD_NO="fd_no";
	public static final String TAG_FL_EXT="fl_ext";
	public static final String TAG_FL_MEMO="fl_memo";
	public static final String TAG_FL_NAME_LOCAL="fl_name_local";
	public static final String TAG_FL_NAME="fl_name";
	public static final String TAG_FL_NO="fl_no";
	public static final String TAG_FL_NO_LINK="fl_no_link";
	public static final String TAG_FL_SIZE="fl_size";
	public static final String TAG_OWNER_ID="owner_id";
	public static final String TAG_PARENT_FD_NO="parent_fd_no";
	public static final String TAG_SHOW_TYPE="show_type";
	public static final String TAG_UPLOAD_DATE="upload_date";
	public static final String TAG_URL="url";

	/* Field names Chinese */
	public static final String TAG_CREATE_DATE_CN="创建日期";
	public static final String TAG_FD_MEMO_CN="文件夹备注";
	public static final String TAG_FD_NAME_LOCAL_CN="文件夹物理路径";
	public static final String TAG_FD_NAME_CN="文件夹名";
	public static final String TAG_FD_NO_CN="文件夹编号";
	public static final String TAG_FL_EXT_CN="文件扩展名";
	public static final String TAG_FL_MEMO_CN="文件备注";
	public static final String TAG_FL_NAME_LOCAL_CN="文件名物理路径";
	public static final String TAG_FL_NAME_CN="文件名";
	public static final String TAG_FL_NO_CN="文件编号";
	public static final String TAG_FL_NO_LINK_CN="物理文件编号";
	public static final String TAG_FL_SIZE_CN="文件大小";
	public static final String TAG_OWNER_ID_CN="所有者编号";
	public static final String TAG_PARENT_FD_NO_CN="父文件夹编号";
	public static final String TAG_SHOW_TYPE_CN="显示类型";
	public static final String TAG_UPLOAD_DATE_CN="上传日期";
	public static final String TAG_URL_CN="URL";

	///////// FileUpload ////////////////////

	static{
		/* add other tag names */
		dict.put(TAG_DOWNLOAD_FILE_NO, TAG_DOWNLOAD_FILE_NO_CN);
		dict.put(TAG_DOWNLOAD_AS_ATTACHMENT, TAG_DOWNLOAD_AS_ATTACHMENT_CN);
		dict.put(TAG_DOWNLOAD_FILENAME_ENCODING_TYPE, TAG_DOWNLOAD_FILENAME_ENCODING_TYPE_CN);

		/* Add field names */
		dict.put(TAG_CREATE_DATE, TAG_CREATE_DATE_CN);
		dict.put(TAG_FD_MEMO,TAG_FD_MEMO_CN);
		dict.put(TAG_FD_NAME_LOCAL,TAG_FD_NAME_LOCAL_CN);
		dict.put(TAG_FD_NAME,TAG_FD_NAME_CN);
		dict.put(TAG_FD_NO,TAG_FD_NO_CN);
		dict.put(TAG_FL_EXT,TAG_FL_EXT_CN);
		dict.put(TAG_FL_MEMO,TAG_FL_MEMO_CN);
		dict.put(TAG_FL_NAME,TAG_FL_NAME_LOCAL_CN);
		dict.put(TAG_FL_NAME,TAG_FL_NAME_CN);
		dict.put(TAG_FL_NO,TAG_FL_NO_CN);
		dict.put(TAG_FL_NO_LINK,TAG_FL_NO_LINK_CN);
		dict.put(TAG_FL_SIZE,TAG_FL_SIZE_CN);
		dict.put(TAG_OWNER_ID,TAG_OWNER_ID_CN);
		dict.put(TAG_PARENT_FD_NO,TAG_PARENT_FD_NO_CN);
		dict.put(TAG_SHOW_TYPE,TAG_SHOW_TYPE_CN);
		dict.put(TAG_UPLOAD_DATE,TAG_UPLOAD_DATE_CN);
		dict.put(TAG_URL,TAG_URL_CN);
	}


}
