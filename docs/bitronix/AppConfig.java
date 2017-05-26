package com.qk.base.spring;


<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-jta-bitronix</artifactId>
</dependency>

import bitronix.tm.BitronixTransactionManager;
import bitronix.tm.TransactionManagerServices;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Description：
 * Created by hqhan on 2017/5/26 0026.
 */

//@EnableAspectJAutoProxy(proxyTargetClass = true)
//@EnableTransactionManagement
//@ComponentScan(basePackages = { "com.qk"}, includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class))
@Configuration
@MapperScan
public class AppConfig extends BaseApplicationConfig implements TransactionManagementConfigurer {


    @Bean("transactionManager")
    @Primary
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        JtaTransactionManager txMgr = new JtaTransactionManager();
        txMgr.setTransactionManager(bitronixTransactionManager());
        txMgr.setUserTransaction(bitronixTransactionManager());
        return txMgr;
    }

//    @Bean("bitronixConfig")
//    public bitronix.tm.Configuration bitronixConfig() {
//        bitronix.tm.Configuration config = TransactionManagerServices.getConfiguration();
//        config.setLogPart1Filename("logs/btm1.tlog");
//        config.setLogPart2Filename("logs/btm2.tlog");
//        config.setDefaultTransactionTimeout(60);
//        return config;
//    }

    @Bean
    public DataSource dataSource(ConfigProperties configProperties){
            Properties driverProps = new Properties();
            driverProps.put("URL", configProperties.getURL());
            driverProps.put("user", configProperties.getUser());
            driverProps.put("password", configProperties.getPassword());
        return createDataSource( configProperties.getUniqueName(), configProperties.getDataSourceClass(), configProperties, driverProps );
    }



//    @DependsOn("bitronixConfig")
    @Bean(value = "bitronixTransactionManager", destroyMethod = "shutdown")
    public BitronixTransactionManager bitronixTransactionManager() {
        return TransactionManagerServices.getTransactionManager();
    }

}
