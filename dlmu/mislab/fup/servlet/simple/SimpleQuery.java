package dlmu.mislab.fup.servlet.simple;

import java.util.List;

import dlmu.mislab.fup.FupDict;
import dlmu.mislab.fup.model.SimpleFileBean;
import dlmu.mislab.fup.model.param.SimpleQueryParam;
import dlmu.mislab.orm.Bn;
import dlmu.mislab.tool.Str;

public class SimpleQuery {

	private static final String SQL_QUERY_ONE="SELECT * FROM " + FupDict.FUP_TABLE_FILE + " where " + FupDict.FUP_TAG_FILE_NO + "=? ";
	public static SimpleFileBean queryOne(Long flNo){
		 SimpleFileBean rtn= Bn.SelectOne(SimpleFileBean.class, SQL_QUERY_ONE, flNo);
		 return rtn;
	}
	
	private static final String SQL_QUERY="SELECT * FROM " + FupDict.FUP_TABLE_FILE + " where 1=1";
	public static List<SimpleFileBean> query(SimpleQueryParam pm){
		StringBuilder buf=new StringBuilder(200);
		buf.append(SQL_QUERY);
		Object[] params=new Object[2];		
		if(!Str.isNullOrEmpty(pm.getQry_fl_name())){
			buf.append(" AND ").append(FupDict.TAG_FL_NAME).append(" LIKE '%").append(pm.getQry_fl_name()).append("%'");
		}
		buf.append(" AND ").append(FupDict.TAG_UPLOAD_DATE).append(" >?");
		buf.append(" AND ").append(FupDict.TAG_UPLOAD_DATE).append(" <?");
		params[0]=pm.getQry_start();
		params[1]=pm.getQry_end();
		List<SimpleFileBean> rtn =Bn.Select(SimpleFileBean.class, buf.toString(), params);
		return rtn;
	}

}
