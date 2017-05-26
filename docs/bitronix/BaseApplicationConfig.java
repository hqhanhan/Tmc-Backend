package com.qk.base.spring;

import bitronix.tm.resource.jdbc.PoolingDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description：
 * Created by hqhan on 2017/5/26 0026.
 */

public abstract class BaseApplicationConfig {


	/**
	 * 初始化配置数据源
	 * @param uniqueName
	 * @param dataSourceClass
	 * @param configProperties
	 * @param props
	 * @return
	 */
    protected DataSource createDataSource(String uniqueName,String dataSourceClass,ConfigProperties configProperties,Properties props ){
        PoolingDataSource dataSource = new bitronix.tm.resource.jdbc.PoolingDataSource();
		dataSource.setUniqueName(uniqueName);
		dataSource.setClassName(dataSourceClass);
		dataSource.setMaxPoolSize(configProperties.getMaxPoolSize());
		dataSource.setAllowLocalTransactions(true);
		dataSource.setAcquisitionTimeout(30);
		dataSource.setPreparedStatementCacheSize(20);
		dataSource.setAcquisitionInterval(5);
		dataSource.setEnableJdbc4ConnectionTest(true);
		dataSource.setAcquireIncrement(5);
		dataSource.setMaxIdleTime(30);
		dataSource.setShareTransactionConnections(true);
		dataSource.setDriverProperties(props);
		dataSource.init();
		return dataSource;
    }
}
