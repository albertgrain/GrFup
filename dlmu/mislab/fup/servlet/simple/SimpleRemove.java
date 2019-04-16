package dlmu.mislab.fup.servlet.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.SimpleFileBean;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;

public class SimpleRemove {
	public static final IResponse removeFile(Long flNo, String userId, String role){
		if(flNo==null){
			return new Err(FupSimpleError.FUP_SIMPLE_WRONG_FILENO);
		}
		if(userId==null){
			return new Err(FupSimpleError.FUP_SIMPLE_NULL_USERID);
		}
		if(role==null){
			return new Err(FupSimpleError.FUP_SIMPLE_NULL_ROLE);
		}
		SimpleFileBean sb=new SimpleFileBean();
		sb.setFl_no(flNo);
		sb=Bn.SelectOne(sb);
		if(sb==null){
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_NOT_FOUND);
		}
		if(sb.getLocked()){
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_LOCKED);
		}
		
		if(!userId.equals(sb.getOwner_id()) && 
				!role.equals(FupDict.FUP_MANAGER_ROLE)){
			return new Err(FupSimpleError.FUP_SIMPLE_DELETE_NOT_OWNER);
		}
		
		if(!Bn.Delete(sb)){
			return new Err(FupSimpleError.FUP_SIMPLE_DELETE_DB_FAILED);
		}
		
		if(SimpleTool.removeFile(SimpleTool.generateFullPath(sb))){
			Logger logger = LoggerFactory.getLogger(SimpleRemove.class);
			logger.warn("User: "+ userId + " deleted file [" + sb.getFd_no()+"] "+ sb.getFl_name());
			return new Ok("文件成功删除");
		}else{
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_DELETE_FAILED);
		}
		
	}

}
