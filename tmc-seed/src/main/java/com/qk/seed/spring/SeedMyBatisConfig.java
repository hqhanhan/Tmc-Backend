package com.qk.seed.spring;

import com.qk.base.spring.ConfigProperties;
import com.qk.base.spring.MyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Description：
 * Created by hqhan on 2017/5/26 0026.
 */

@Configuration
@MapperScan(basePackages = "com.qk.seed.dao.mapper", sqlSessionTemplateRef = "seedSqlSessionTemplate")
public class SeedMyBatisConfig extends MyBatisConfig {


    @Primary
    @Bean(name = "seedDataSource")
    @Override
    public DataSource dateSource(ConfigProperties baseConfig)throws SQLException {
        return super.createDataSource(baseConfig,"seedDataSource");
    }


    @Bean(name = "seedSqlSessionFactory")
    @Override
    public SqlSessionFactory sqlSessionFactory(@Qualifier("seedDataSource")DataSource dataSource) throws Exception  {
        return super.createSqlSessionFactory(dataSource,"/mapper/","com.qk.seed.model");
    }

    @Bean(name = "seedSqlSessionTemplate")
    @Override
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("seedSqlSessionFactory")SqlSessionFactory sqlSessionFactory)  throws Exception{
        return  super.createSqlSessionTemplate(sqlSessionFactory);
    }
}
