package dlmu.mislab.fup.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dlmu.mislab.config.Log4JConfigure;
import dlmu.mislab.fup.model.FupFileModel;

public abstract class FupDaoBase{
	protected static Logger logger = null;
	static{
		Log4JConfigure.init();
		//DbConfigurator.init();
		logger = LoggerFactory.getLogger(FupDaoBase.class);
	}

	
	public int InsertThenGetKey(FupFileModel bean){
		throw new RuntimeException("TODO");
	}
}
