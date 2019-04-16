// By GuRui on 2015-2-16 下午4:56:33
package dlmu.mislab.fup.dao;

import java.util.List;

import dlmu.mislab.common.ConfigBase;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.tool.Str;

public class FupFileDao extends FupDaoBase{
	public int InsertThenGetKey(FupFileModel bean){
		Integer rtn = (Integer)Bn.insertThenGetOneValue(bean);
		if(rtn==null){
			return -1;
		}else{
			return rtn;
		}
	}

	public int InsertThenGetKeyAndUpdateFileNameLocal(FupFileModel bean){
		int fileNo=this.InsertThenGetKey(bean);
		if(fileNo<=0){
			return fileNo;
		}
		bean.setFl_no(fileNo);
		if(this.updateFileNameLocalToPhysical(bean,fileNo)){
			return fileNo;
		}else{
			return -1;
		}
	}

	private static final String SQL_updateFileNameLocalToPhysical="update "+ FupDict.FUP_TABLE_FILE +" set fl_name_local=? where fl_no=?";
	private boolean updateFileNameLocalToPhysical(FupFileModel bean, int fileNo){
		Integer folderNo=bean.getFd_no();
		String folderNameLocal=null;
		if(folderNo==FupDict.FUP_MY_FOLDER_NO){
			folderNameLocal=ConfigBase.PATH_DELIMETER+FupDict.FUP_FOLDER_MY_NAME+ConfigBase.PATH_DELIMETER+bean.getOwner_id();
			bean.setFl_name_local(folderNameLocal+"\\"+fileNo+"."+bean.getFl_ext());
		}

		boolean rtn =Bn.executeNoQuery(SQL_updateFileNameLocalToPhysical,bean.getFl_name_local(),bean.getFl_no());
		if(!rtn){
			logger.error("Failed to update field 'fd_name_local' after freshly inserted.");
		}
		return rtn;
	}

	private static final String SQL1_getChildFiles="select * from "+FupDict.FUP_TABLE_FOLDER+" where fd_no=?";
	private static final String SQL2_getChildFiles="select * from "+FupDict.FUP_TABLE_FOLDER+" where fd_no=? and (owner_id='0' or owner_id is null or owner_id=?) ";
	public List<FupFileModel> getChildFiles(int folderNo, String ownerId){
		String sql;
		if(folderNo==FupDict.FUP_SHARE_FOLDER_NO){
			sql=SQL1_getChildFiles;
		}else{
			sql=SQL2_getChildFiles;
		}
		return Bn.Select(FupFileModel.class, sql, folderNo, ownerId);
	}

	
	
	private static final String SQL1_deleteBatch="select fl_no, fl_no_link, fl_name_local, owner_id, locked from "+FupDict.FUP_TABLE_FILE+" where (owner_id=? or owner_id is null) and fl_no in (";
	private static final String SQL2_deleteBatch=") order by fl_no_link DESC, fl_no DESC ";
	/***
	 * 
	 * By GuRui on 2015-4-19 下午10:31:10
	 * @param fileNoList
	 * @param userId 删除时session中存放的用户id。如果与数据库ownerId不符，则不能删除
	 * @return
	 */
	public List<FupFileModel> deleteBatch(List<Integer> fileNoList, String userId){
		if(fileNoList==null || Str.isNullOrEmpty(userId)){
			return null;
		}
		StringBuilder buf=new StringBuilder();
		for(int i: fileNoList){
			buf.append(i).append(",");
		}
		buf.deleteCharAt(buf.length()-1); // delete the comma at tail.

		String sql= SQL1_deleteBatch + buf.toString() + SQL2_deleteBatch;
		List<FupFileModel> fileList=Bn.Select(FupFileModel.class,sql, userId);

		if(fileNoList.size()!=fileList.size()){
			logger.error("待删除文件列表长度和从数据库查询得到的可删除文件列表不一致");
			return null;
		}
		
		for(FupFileModel f : fileList){
			if(f.getLocked()){
				logger.error("待删除文件列表中存在被认为设定为锁定的文件.删除操作被取消.");
				return null;
			}
		}
		
		boolean rtn = Bn.Delete(fileList.toArray(new FupFileModel[0]), true, true);

		if(!rtn){
			logger.error("从数据库删除上传文件信息失败");
			return null;
		}
		return fileList;
	}

	private static final String SQL_rename="update tb_fup_file set fl_name=CONCAT(?,'.',fl_ext) where fl_no=?";
	public boolean rename(int fileNo, String newName){
		return Bn.executeNoQuery(SQL_rename, newName, fileNo);
	}
	
	
	public boolean Delete(FupFileModel bean){
		return Bn.Delete(bean);
	}
	
	public List<FupFileModel> SelectFilesByFileNoList(String fileNoList){
		String sql="select * from tb_fup_file where fl_no in ("+fileNoList+") ";
		return Bn.Select(FupFileModel.class, sql);
	}
}
