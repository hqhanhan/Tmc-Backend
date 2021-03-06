package com.qk.seed;

import com.qk.seed.config.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Description：
 * Created by hqhan on 2017/5/24 0024.
 */
@SpringBootApplication
@EnableTransactionManagement
public class Start extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Start.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Start.class, args);
    }

}
