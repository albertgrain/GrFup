// By GuRui on 2015-12-9 上午8:55:18
package dlmu.mislab.fup;

import java.util.List;

public interface IDeleteFileCallBack{
	
	public boolean beforeUpload(List<Integer> fileList, String extra);
	
	public boolean afterUpload(List<Integer> fileList, String extra);

	public String getLastError();
}
