// By GuRui on 2018-3-16 上午11:10:32
package dlmu.mislab.fup.servlet.simple;

import java.util.Date;

/***
 * 用于限制SingletonFupAddToPath和SingletonFupDeleteFromPath，同时只允许进行一个操作
 * By GuRui on 2018-3-16 上午11:49:56
 *
 */
enum SingletonSignal {
	INSTANCE;
	public static final long INTERVAL=20 * 1000; // 30 seconds
	private long timestamp=new Date().getTime();
	private boolean occupied=false;
	
	public synchronized boolean getToken(){
		if(this.occupied){
			long dif=new Date().getTime() - timestamp;
			if(dif > INTERVAL){
				this.setToken();
				return true;
			}else{
				return false;
			}
		}else{
			this.setToken();
			return true;
		}
	}
	
	private void setToken(){
		this.timestamp=new Date().getTime();
		this.occupied=true;
	}
	
	public void release(){
		this.occupied=false;
	}
}
