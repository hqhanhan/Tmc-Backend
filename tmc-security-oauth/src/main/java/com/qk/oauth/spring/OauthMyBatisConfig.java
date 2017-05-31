package com.qk.oauth.spring;

import com.qk.seed.base.spring.ConfigProperties;
import com.qk.seed.base.spring.MyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Description：
 * Created by hqhan on 2017/5/26 0026.
 */


@Configuration
@MapperScan(basePackages = "com.qk.oauth.dao.mapper", sqlSessionTemplateRef = "oauthSqlSessionTemplate")
public class OauthMyBatisConfig extends MyBatisConfig {


    @Autowired
    private OauthConfigProperties config;


    @Override
    @Bean(name = "oauthDataSource")
    public DataSource dateSource()throws SQLException {
        return super.createDataSource(config,"oauthDataSource");
    }

    @Override
    @Bean(name = "oauthSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("oauthDataSource")DataSource dataSource) throws Exception  {
        return super.createSqlSessionFactory(dataSource,"/oauth/mapper/","com.qk.oauth.model");
    }

    @Override
    @Bean(name = "oauthSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("oauthSqlSessionFactory")SqlSessionFactory sqlSessionFactory)  throws Exception{
        return  super.createSqlSessionTemplate(sqlSessionFactory);
    }
}
