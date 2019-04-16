package dlmu.mislab.fup.servlet.simple;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.common.FupSimpleError;
import dlmu.mislab.fup.model.param.SimpleAddParam;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.web.interact.IResponse;
import dlmu.mislab.web.response.Err;
import dlmu.mislab.web.response.Ok;

public class SimpleAdd {
	public static final IResponse addFile(SimpleAddParam bean, String userId, String role){
		if(bean==null){
			return new Err("缺少上传用户信息");
		}
		boolean isPrivate = bean.getIs_private()==null?true:bean.getIs_private();
		if(!isPrivate){
			if(!FupDict.FUP_MANAGER_ROLE.equals(role)){
				return new Err(FupSimpleError.FUP_SIMPLE_ADD_TO_PUB_NOT_ALLOWED);
			}
		}
		ParsedFile pFile=null;
		try{
			pFile=SimpleTool.parseImgDataInfo(bean);
		}catch(Exception e){
			return new Err(e.getMessage());
		}
		
		pFile.info.setOwner_id(userId);
		
		Long flNo =Bn.insertThenGetOneValue(pFile.info);
		if(flNo==null){
			return new Err(FupSimpleError.FUP_SIMPLE_GEN_FILENO_FAILED);
		}
		String[] paths=null;
		if(isPrivate){
			paths= new String[2];
			paths[0]=FupDict.FUP_FOLDER_MY_NAME;
			paths[1]=userId;
		}else{
			paths= new String[1];
			paths[0]=FupDict.SIMPLE_FOLDER_NAME;
		}
		pFile.info.setFl_no(flNo);
		
		int size=SimpleTool.writeFileToDisk(pFile.base64File, flNo+"."+ pFile.info.getFl_ext(),paths);
		if(size==-1){
			Bn.Delete(pFile.info);
			return new Err(FupSimpleError.FUP_SIMPLE_FILE_WRITE_FAILED);
		}
		pFile.info.setFl_size(size);
		pFile.info.setFl_name_local(SimpleTool.generateLocalName(pFile.info, isPrivate));
		boolean rtn = Bn.Update(pFile.info);
		if(rtn){
			//return new Ok(flNo.toString());
			pFile.info.setFl_name_local(null);
			return new Ok(pFile.info);
		}else{
			Bn.Delete(pFile.info);
			return new Err(FupSimpleError.FUP_SIMPLE_LAST_DB_UPDATE_FAILED);
		}
	}
}
