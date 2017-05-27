package com.qk.oauth.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Description：
 * Created by hqhan on 2017/5/26 0026.
 */

@EnableSwagger2
@Configuration
public class OauthSwaggerConfig {

    @Bean
    public Docket addUserDocket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(
                "restful API",
                "API Document管理",
                "V3.8.0",
                "hqhan8080@163.com",
                "我的邮箱",
                "",
                "");
        docket.apiInfo(apiInfo);
        return docket;
    }
}
