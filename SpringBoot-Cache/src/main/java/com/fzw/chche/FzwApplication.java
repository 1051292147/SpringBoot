package com.fzw.chche;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan(value = "com.fzw.chche.mapper")
@EnableCaching
public class FzwApplication {

    public static void main(String[] args) {
        SpringApplication.run(FzwApplication.class, args);
    }

}
