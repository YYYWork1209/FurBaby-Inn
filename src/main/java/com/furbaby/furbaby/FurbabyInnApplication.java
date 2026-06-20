package com.furbaby.furbaby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

// 展示禁用过滤器，最后开发完成后再考虑登录设计
//@ServletComponentScan
@EnableScheduling
@SpringBootApplication
public class FurbabyInnApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurbabyInnApplication.class, args);
    }

}
