// By GuRui on 2015-4-26 上午1:01:46
package dlmu.mislab.fup.servlet;

import java.util.ArrayList;
import java.util.List;

import dlmu.mislab.common.IJson;
import dlmu.mislab.fup.model.FupFileModel;

class FileList implements IJson{
	private List<FupFileModel> fileList=new ArrayList<FupFileModel>(5);

	public boolean add(FupFileModel flModel){
		return this.fileList.add(flModel);
	}
	
	public List<FupFileModel> getFileList() {
		return fileList;
	}

	public void setFileList(List<FupFileModel> fileList) {
		this.fileList = fileList;
	}
	
}
