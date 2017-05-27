package com.qk.oauth.spring;

import com.qk.base.spring.ConfigProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by hqhan on 2017/5/26 0026.
 */

@ConfigurationProperties(prefix = "oauth.datasource")
public  class OauthConfigProperties extends ConfigProperties {


}
