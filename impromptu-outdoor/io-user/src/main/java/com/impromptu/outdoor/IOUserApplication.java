package com.impromptu.outdoor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.impromptu.outdoor.dao")
@SpringBootApplication
public class IOUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(IOUserApplication.class, args);
    }

}
