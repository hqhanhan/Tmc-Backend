package com.qk.seed.spring;

import com.qk.base.api.ConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hqhan on 2017/5/26 0026.
 */

@ConfigurationProperties(prefix = "seed.datasource")
public  class SeedConfigProperties extends ConfigProperties {

}
