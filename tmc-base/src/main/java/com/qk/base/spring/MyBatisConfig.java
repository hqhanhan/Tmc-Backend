package com.qk.base.spring;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.sql.SQLException;

public abstract  class MyBatisConfig {

	public abstract DataSource  dateSource(ConfigProperties baseConfig )throws SQLException ;
	public abstract SqlSessionFactory  sqlSessionFactory( DataSource dataSource)throws Exception;
	public abstract SqlSessionTemplate  sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)throws Exception;


	protected DataSource createDataSource(ConfigProperties baseConfig,String uniqueResourceName) throws SQLException {

		DruidXADataSource dataSource = new DruidXADataSource();
		dataSource.setDriverClassName(baseConfig.getDriverClassName());
		dataSource.setUrl(baseConfig.getUrl());
		dataSource.setUsername(baseConfig.getUsername());
		dataSource.setPassword(baseConfig.getPassword());

		AtomikosDataSourceBean wrapped  = new AtomikosDataSourceBean();
		wrapped .setXaDataSource(dataSource);
		wrapped .setUniqueResourceName(uniqueResourceName);
		wrapped .setXaDataSourceClassName(baseConfig.getXaDataSourceClassName());
		wrapped .setMinPoolSize(baseConfig.getMinPoolSize());
		wrapped .setMaxPoolSize(baseConfig.getMaxPoolSize());
		wrapped .setMaxLifetime(baseConfig.getMaxLifetime());
		wrapped .setBorrowConnectionTimeout(baseConfig.getBorrowConnectionTimeout());
		wrapped .setLoginTimeout(baseConfig.getLoginTimeout());
		wrapped .setMaintenanceInterval(baseConfig.getMaintenanceInterval());
		wrapped .setMaxIdleTime(baseConfig.getMaxIdleTime());
		wrapped .setTestQuery(baseConfig.getTestQuery());
		return wrapped ;
	}


	protected SqlSessionFactory createSqlSessionFactory(DataSource dataSource,String classPathXml,String aliases)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		bean.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources("classpath:" + classPathXml + "*.xml"));
		bean.setTypeAliasesPackage(aliases);
		return bean.getObject();
	}


	protected SqlSessionTemplate createSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	protected MapperScannerConfigurer createMapperScannerConfigurer(String basePackage,String  sqlSessionFactoryBeanName){
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage(basePackage);
		mapperScannerConfigurer.setSqlSessionFactoryBeanName(sqlSessionFactoryBeanName);
		return mapperScannerConfigurer;
	}
}
