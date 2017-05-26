package com.qk.base.spring;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description：base项目 properties数据配置
 * Created by hqhan on 2017/5/26 0026.
 */


@Accessors(chain = true)
@NoArgsConstructor
@Getter
@Setter
@ToString
public  class ConfigProperties {

    /**
     * 关于alibaba druid 配置信息
     */
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private int initialSize;
    private int minIdle;
    private int maxActive;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private String filters;

    /**
     * 关于atomikos 配置信息
     */
    private String xaDataSourceClassName;
    private String uniqueName;
    private int minPoolSize;
    private int maxPoolSize;
    private int maxLifetime;
    private int borrowConnectionTimeout;
    private int loginTimeout;
    private int maintenanceInterval;
    private int maxIdleTime;
    private String testQuery;





}
