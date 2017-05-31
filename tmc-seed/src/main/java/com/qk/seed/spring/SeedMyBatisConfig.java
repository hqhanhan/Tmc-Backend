package com.qk.seed.spring;

import com.qk.base.spring.MyBatisConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SeedConfigProperties config;

    @Override
    @Primary
    @Bean(name = "seedDataSource")
    public DataSource dateSource()throws SQLException {
        return super.createDataSource(config,"seedDataSource");
    }

    @Override
    @Bean(name = "seedSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("seedDataSource")DataSource dataSource) throws Exception  {
        return super.createSqlSessionFactory(dataSource,"/seed/mapper/","com.qk.seed.model.entity");
    }

    @Override
    @Bean(name = "seedSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("seedSqlSessionFactory")SqlSessionFactory sqlSessionFactory)  throws Exception{
        return  super.createSqlSessionTemplate(sqlSessionFactory);
    }
}
