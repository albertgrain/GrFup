// By GuRui on 2015-12-9 上午2:47:04
package dlmu.mislab.fup;

import java.util.List;

import dlmu.mislab.fup.model.FupFileModel;


public interface IUploadFileCallBack{
	
	public boolean beforeUpload(List<FupFileModel> fileList, String extra);
	
	public boolean afterUpload(List<FupFileModel> fileList, String extra);

	public String getLastError();
}
