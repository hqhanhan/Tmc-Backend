package com.qk.seed.spring;

import com.qk.base.spring.ConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Description：base项目 properties数据配置
 * Created by hqhan on 2017/5/26 0026.
 */

@ConfigurationProperties(prefix = "seed.datasource")
public  class SeedConfigProperties extends ConfigProperties {

}
