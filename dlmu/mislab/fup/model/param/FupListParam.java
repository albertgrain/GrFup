// By GuRui on 2015-2-18 上午3:13:52
package dlmu.mislab.fup.model.param;

import dlmu.mislab.validation.NotNull;
import dlmu.mislab.web.interact.IParameter;

/***
 * used for both list File / list Folder
 * By GuRui on 2015-4-20 上午9:29:23
 *
 */
public class FupListParam implements IParameter {
	@NotNull
	private Integer fd_no=0; //Default is 0: MY folder
	
//	@NoValidation
//	private String ownerId; //The owenr id is assgined according to id in Session
	
	public Integer getFd_no() {
		return fd_no;
	}

	public void setFd_no(Integer fd_no) {
		this.fd_no = fd_no;
	}
}
