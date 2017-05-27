package com.qk.oauth;

import com.qk.oauth.spring.OauthConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Description：
 * Created by hqhan on 2017/5/24 0024.
 */
@SpringBootApplication
@EnableConfigurationProperties(value = { OauthConfigProperties.class })
public class Start extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Start.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
    }

}
