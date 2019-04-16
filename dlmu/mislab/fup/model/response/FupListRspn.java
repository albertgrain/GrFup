// By GuRui on 2015-4-20 上午9:35:47
package dlmu.mislab.fup.model.response;

import java.util.List;

import dlmu.mislab.fup.model.FupFileModel;
import dlmu.mislab.fup.model.FupFolderModel;
import dlmu.mislab.web.interact.IResponse;

/**
 * This model is designed for future use. Return both files and folders within a designated folder.
 * By GuRui on 2015-4-20 上午9:36:59
 *
 */
public class FupListRspn implements IResponse{
	private List<FupFolderModel> child_folders;
	private List<FupFileModel> child_files;
	public List<FupFolderModel> getChild_folders() {
		return this.child_folders;
	}
	public void setChild_folders(List<FupFolderModel> child_folders) {
		this.child_folders = child_folders;
	}
	public List<FupFileModel> getChild_files() {
		return this.child_files;
	}
	public void setChild_files(List<FupFileModel> child_files) {
		this.child_files = child_files;
	}

}
