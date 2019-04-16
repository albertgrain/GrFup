// By GuRui on 2015-4-26 上午12:56:21
package dlmu.mislab.fup.servlet;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dlmu.mislab.common.LogicError;
import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.FupError;
import dlmu.mislab.fup.dao.FupFileDao;
import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.tool.Str;

public class FTools {
	private static Logger logger=LoggerFactory.getLogger(FTools.class);
	/***
	 * If folder no is not 0, then add two entries. Otherwise add only one entry
	 * By GuRui on 2015-4-17 上午8:44:13
	 * @param fileList
	 * @return File list model with file_nos
	 */
	static List<FupFileModel> saveUploadFileModelToDataBase(List<FupFileModel> fileList){
		List<FupFileModel> rtn=new LinkedList<FupFileModel>();
		FupFileDao dao=new FupFileDao();
		for(FupFileModel file: fileList){
			if(file.getFd_no()==FupDict.FUP_MY_FOLDER_NO){//Upload to folder MY(0)
				int result=dao.InsertThenGetKeyAndUpdateFileNameLocal(file);
				if(result>0){ //insert successful 
					rtn.add(file);
					continue;
				}
			}else{ //Upload to folder other than MY, add two entries into tb_file
				int originalFolderNo=file.getFd_no();
				file.setFd_no(FupDict.FUP_MY_FOLDER_NO); //reset the fd_no to 0 to ensure correctly create fl_path_local
				int result=dao.InsertThenGetKeyAndUpdateFileNameLocal(file);
				//No need to add the MY file into the return list;
				if(result>=0){//if the first entry is successful
					//file.setFl_no(result); //then append the first entry into the feedback list
					//myFile=file.copy();
					
					file.setFd_no(originalFolderNo);
					//file.setFl_no_link(myFile.getFl_no());
					file.setFl_no_link(result); // Set link to file in MY folder
					file.setFl_no(null); //New fl_no will be generated later
					result=dao.InsertThenGetKeyAndUpdateFileNameLocal(file);
					if(result>0){ //if the second entry insert is successful
						rtn.add(file); //then insert the file info in MY folder into return list
					}
				}
			}
		}
		return rtn;
	}
	
	/**
	 * Save file model info into db. If the model is a physical model, then add two entries.
	 * By GuRui on 2015-4-26 下午2:56:13
	 * @param fileList
	 * @param toFolderNo
	 * @return null if something wrong.
	 */
	static List<FupFileCopyModel> saveCopiedFileModelToDataBase(List<FupFileModel> fileList, int toFolderNo, String ownerId,boolean isCurrentUserOwner){
		List<FupFileCopyModel> rtn=new LinkedList<FupFileCopyModel>();
		FupFileDao dao=new FupFileDao();
		for(FupFileModel file: fileList){
			file.setUpload_date(new Date());
			file.setFd_no(toFolderNo);
			if(isCurrentUserOwner){
				if(file.getFl_no_link()==0){ //If current file is physical/MY, change the link to current file
					file.setFl_no_link(file.getFl_no());
				}
			}else{
				file.setFl_no_link(FupDict.FUP_DEFAULT_LINK_FILE_NO);
				file.setOwner_id(ownerId);
			}
			file.setFl_no(null);

			int result=0;			
			Integer tempFolderNo=file.getFd_no();
			if(!isCurrentUserOwner){//Owner of source and dest are different, copy forced
				file.setFd_no(FupDict.FUP_MY_FOLDER_NO); //reset the fd_no to 0 to ensure correctly create fl_path_local
				FupFileCopyModel rtnItem=new FupFileCopyModel();
				rtnItem.setFileSource(file.getFl_name_local());
				
				result=dao.InsertThenGetKeyAndUpdateFileNameLocal(file);
				
				rtnItem.setFileDest(file.getFl_name_local());
				rtn.add(rtnItem);
			}
			if(result<0){
				return null;  //Error
			}
			
			file.setFd_no(tempFolderNo);
			file.setFl_no(null); //New fl_no will be generated later
			if(file.getFd_no()>0){ //If the dest is MY, do not need to generate it twice.
				if(!isCurrentUserOwner){//If current user is owner, the link has already set, no need to set again
					file.setFl_no_link(file.getFl_no()); //previous fileNo is physical fileNo
				}
				result=dao.InsertThenGetKey(file);
			}
			if(result<0){ //if the second entry insert is successful
				return null; //Error occured during InsertThenGetKeyAndUpdateFileNameLocal (2)
			}
		}
		return rtn;
	}
	
	static LogicError removeFileModelFromDb(List<FupFileModel> fileList2,List<FupFileModel> fileList3){
		for(FupFileModel file2:fileList2){
			boolean found=false;
			for(FupFileModel file3:fileList3){
				if(file2.getFl_name().equals(file3.getFl_name())){
					found=true;
					break;
				}
			}
			if(!found){
				FupFileDao dao=new FupFileDao();
				boolean x=dao.Delete(file2);
				if(!x){
					logger.error("******* 删除未成功上传文件信息失败！fl_name:"+file2.getFl_name()+ " fl_no:"+file2.getFl_no()+" fd_no:"+file2.getFd_no());
					return FupError.FUP_DELETE_FROM_DATABASE_FAILED;
				}
			}
		}
		return null;
	}
	
	static LinkedList<Integer> convertStrFileNoListToIntFileNoList(String fileNoList){
		String[] fileNos=fileNoList.split(FupDict.FUP_DELIMETER_FILE_NO_LIST);
		LinkedList<Integer> fnos=new LinkedList<Integer>();
		for(String sfno:fileNos){
			if(!Str.isNullOrEmpty(sfno)){
				Integer fno=null;
				try{
					fno=Integer.parseInt(sfno);
				}catch(NumberFormatException e){
					return null;
				}
				fnos.add(fno);
			}
		}
		return fnos;
	}
}
