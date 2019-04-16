// By GuRui on 2015-2-18 上午3:23:19
package dlmu.mislab.fup.dao;

import java.util.Date;
import java.util.List;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.FupFolderModel;
import dlmu.mislab.orm.Bn;

public class FupFolderDao extends FupDaoBase {
	
	public List<FupFolderModel> getChildFolders(int parentFolderNo, String ownerId){
		return Bn.Select(FupFolderModel.class, "select fd_no,fd_name,create_date,owner_id,fd_memo from tb_fup_folder where fd_no>0 and parent_fd_no=? and owner_id=? order by parent_fd_no DESC, fd_no DESC", parentFolderNo, ownerId);
	}

	public List<FupFolderModel> getAllVisibleFolders(String ownerId){
		return Bn.Select(FupFolderModel.class, "SELECT fd_no,fd_name,create_date,owner_id,fd_memo FROM tb_fup_folder WHERE fd_no>0 AND parent_fd_no>=0 AND (owner_id='0' or owner_id is null or owner_id=?) ORDER BY parent_fd_no DESC, fd_no DESC ", ownerId);
	}

	public boolean isOwner(int folderId, String ownerId){
		Integer rtn=Bn.selectScalar(Integer.class,"SELECT 1 from tb_fup_folder where fd_no=? and owner_id=?", folderId, ownerId);
		return !(rtn==null);
	}

	/**
	 * 
	 * By GuRui on 2015-4-26 下午8:49:38
	 * @param bean
	 * @return -1 when there is already a folder with the same name is the designated folder. Or insert failed.
	 */
	public int createFolder(FupFolderModel bean){
		if(bean==null){
			return -1;
		}
		if(bean.getParent_fd_no()==null){
			bean.setParent_fd_no(FupDict.FUP_MY_FOLDER_NO);
		}

		Object fnm=Bn.selectScalar(FupFolderModel.class, "select fd_name from tb_fup_folder where fd_name=? and parent_fd_no=? and owner_id=?", bean.getFd_name(), bean.getParent_fd_no(), bean.getOwner_id());
		if(fnm!=null){
			if(fnm.toString().equals(bean.getFd_name())){
				logger.warn("相同文件夹下发现同名文件名");
				return -1;
			}
		}
		if(bean.getParent_fd_no()!=0){
			Integer ownerId=Bn.selectScalar(Integer.class, "select owner_id from tb_fup_folder where fd_no=?", bean.getParent_fd_no());
			if(ownerId==null){
				logger.error("数据库中没有找到父目录");
				return -1;
			}
			if(!bean.getOwner_id().equals(ownerId.toString())){
				logger.error("用户非父目录所有者，不能创建新目录. user:"+ownerId);
				return -1;
			}
		}
		bean.setFd_no(null);
		bean.setCreate_date(new Date());
		return Bn.insertThenGetOneValue(bean);
	}

	private static final String SQL_renameFolder="update tb_fup_folder set fd_name=? where fd_no=?";
	public boolean renameFolder(FupFolderModel bean){
		if(bean==null || bean.getFd_no()==null || bean.getFd_no()<=0){
			return false;
		}
		return Bn.executeNoQuery(SQL_renameFolder, bean.getFd_name() ,bean.getFd_no());
	}

	private static final String SQL_deleteFolder_count="select count(fl_no) from tb_fup_file where fd_no=?";
	private static final String SQL_deleteFolder_delete="delete from tb_fup_folder where fd_no=?";
	public String deleteFolder(FupFolderModel bean){
		if(bean==null || bean.getFd_no()==null || bean.getFd_no()<=0){
			return "请正确选择待删除文件夹";
		}
		
		Integer rtn = Bn.selectScalar(Integer.class, SQL_deleteFolder_count, bean.getFd_no());
		if(rtn==null){
			return "查找文件夹内文件时出错";
		}
		int i=Integer.parseInt(rtn.toString());
		if(i>0){
			return "待删除文件夹不为空。请先删除文件";
		}

		boolean test = Bn.executeNoQuery(SQL_deleteFolder_delete, bean.getFd_no());
		if(!test){
			return "待删除文件夹失败。请检查：1.是否有权限 2.其父文件夹是否存在";
		}
		return null;
	}
}
