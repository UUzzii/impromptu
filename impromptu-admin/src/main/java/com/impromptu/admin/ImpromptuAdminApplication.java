package com.impromptu.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.impromptu.admin.dao")
@SpringBootApplication
public class ImpromptuAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImpromptuAdminApplication.class, args);
    }

}
