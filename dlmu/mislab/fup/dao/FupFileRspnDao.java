// By GuRui on 2015-4-19 下午7:24:39
package dlmu.mislab.fup.dao;

import java.util.Iterator;
import java.util.List;

import dlmu.mislab.fup.model.response.FupFileRspn;
import dlmu.mislab.orm.Bn;

public class FupFileRspnDao{
	public List<FupFileRspn> findFileWithLink(List<Integer> originalFileNoList){
		StringBuilder buf=new StringBuilder();
		buf.append("select a.fl_no, a.fl_name, a.fd_no, b.fd_name from tb_fup_file a, tb_fup_folder b where a.fd_no=b.fd_no and fl_no_link<>0 and fl_no_link in (");
		for(int i=0;i<originalFileNoList.size();i++){
			if(i==0){
				buf.append("?");
			}else{
				buf.append(",?");
			}
		}
		buf.append(")");
		List<FupFileRspn> linkedFileNoList= Bn.Select(FupFileRspn.class, buf.toString(), originalFileNoList.toArray());
		
		List<FupFileRspn> rtn=this.bSubstractA(linkedFileNoList, originalFileNoList);
		return rtn;
	}
	
	private List<FupFileRspn> bSubstractA(List<FupFileRspn> B, List<Integer> A){
		Iterator<FupFileRspn> itr= B.iterator();
		while(itr.hasNext()){
			FupFileRspn item=itr.next();
			for(int i=0;i<A.size();i++){
				if(item.getFl_no()==A.get(i)){
					itr.remove();
					break;
				}
			}
		}
		return B;
	}

}
