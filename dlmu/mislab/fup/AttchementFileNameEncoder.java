package dlmu.mislab.fup;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class AttchementFileNameEncoder{
	public static final String URL_ENCODING_MSIE="a";
	public static final String MIME_ENCODING_MOZILLA_FIREFOX="b";
	public static final String URL_ENCODING_WEBKIT_WIN_ANDROID="a";
	public static final String URL_ENCODING_WEBKIT_APPLE="c";
	public static final String ENCODING_UNKNOWN="d";
	public static final String ENCODING_DEFAULT="c";
	
	public static String encode(String encodeType, String fileName, String ext){
        String headerValue=null;
		try {
			//MimeUtility方法适用于火狐，不适用手机和chrome
			if(URL_ENCODING_MSIE.equals(encodeType)){
				headerValue = String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "utf-8"));
			}else if(MIME_ENCODING_MOZILLA_FIREFOX.equals(encodeType)){
				headerValue = String.format("attachment; filename=\"%s\"", MimeUtility.encodeWord(fileName, "utf-8", "Q"));
			}else if(URL_ENCODING_WEBKIT_APPLE.equals(encodeType)){
				headerValue = "attachment; filename*=UTF-8''"+URLEncoder.encode(fileName, "utf-8");
			}else{
				headerValue = "attachment; filename*=UTF-8''"+URLEncoder.encode(fileName, "utf-8");
			}
		} catch (UnsupportedEncodingException e) {
			headerValue=String.format("attachment; filename=\"%s.%s\"", "download",ext);
		}
		return headerValue;
	}
}