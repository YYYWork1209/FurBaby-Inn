package com.furbaby.furbaby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// 展示禁用过滤器，最后开发完成后再考虑登录设计
//@ServletComponentScan
@SpringBootApplication
public class FurbabyInnApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurbabyInnApplication.class, args);
    }

}
