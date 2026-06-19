package com.furbaby.furbaby.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("毛孩驿站 API 文档")
                        .version("1.0.0")
                        .description("毛孩驿站 (Furbaby Inn) 宠物寄养服务平台后端接口文档")
                        .contact(new Contact()
                                .name("YYY")
                                .url("https://github.com/YYYWork1209/FurBaby-Inn")));
    }
}
