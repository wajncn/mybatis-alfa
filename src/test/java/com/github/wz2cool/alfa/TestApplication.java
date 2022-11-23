package com.github.wz2cool.alfa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Frank on 2017/7/15.
 */
@SpringBootApplication
@MapperScan(basePackages = "com.github.wangjin.")
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}
