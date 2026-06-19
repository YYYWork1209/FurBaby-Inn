package com.furbaby.furbaby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication
public class FurbabyInnApplication {

    public static void main(String[] args) {
        SpringApplication.run(FurbabyInnApplication.class, args);
    }

}
