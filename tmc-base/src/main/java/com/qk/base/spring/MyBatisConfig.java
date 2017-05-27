package com.qk.base.spring;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.mysql.jdbc.jdbc2.optional.MysqlXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;

public abstract  class MyBatisConfig {

	public abstract DataSource  dateSource(ConfigProperties baseConfig )throws SQLException ;
	public abstract SqlSessionFactory  sqlSessionFactory( DataSource dataSource)throws Exception;
	public abstract SqlSessionTemplate  sqlSessionTemplate(SqlSessionFactory sqlSessionFactory)throws Exception;




	protected DataSource createDataSource(ConfigProperties baseConfig,String uniqueResourceName) throws SQLException {

		//druid数据源连接
		DruidXADataSource dataSource = new DruidXADataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(baseConfig.getUrl());
		dataSource.setUsername(baseConfig.getUsername());
		dataSource.setPassword(baseConfig.getPassword());
//        dataSource.setInitialSize(baseConfig.getInitialSize());
//        dataSource.setMaxActive(baseConfig.getMaxActive());
//        dataSource.setMaxWait(baseConfig.getMaxWait());
//        dataSource.setTimeBetweenEvictionRunsMillis(baseConfig.getTimeBetweenEvictionRunsMillis());
//        dataSource.setMinEvictableIdleTimeMillis(baseConfig.getMinEvictableIdleTimeMillis());
//        dataSource.setValidationQuery(baseConfig.getValidationQuery());
//        dataSource.setTestWhileIdle(baseConfig.isTestWhileIdle());
//        dataSource.setTestOnBorrow(baseConfig.isTestOnBorrow());
//        dataSource.setTestOnReturn(baseConfig.isTestOnReturn());
//        dataSource.setPoolPreparedStatements(baseConfig.isPoolPreparedStatements());
//        dataSource.setMaxPoolPreparedStatementPerConnectionSize(baseConfig.getMaxPoolPreparedStatementPerConnectionSize());
//        try {
//            druidXADataSource.setFilters(baseConfig.getFilters());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


		//常规数据源连接池
//		MysqlXADataSource dataSource = new MysqlXADataSource();
//		dataSource.setUrl(baseConfig.getUrl());
//		dataSource.setPinGlobalTxToPhysicalConnection(true);
//		dataSource.setPassword(baseConfig.getPassword());
//		dataSource.setUser(baseConfig.getUsername());
//		dataSource.setPinGlobalTxToPhysicalConnection(true);

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
