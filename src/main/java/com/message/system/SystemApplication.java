package com.message.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableCaching
@ComponentScan(basePackages = {"com.message.controller"
                ,"com.message.service"
                ,"com.message.mapper"
                ,"com.message.utils"
                ,"com.message.filter"
                ,"com.message.interceptor"
                ,"com.message.config"
                ,"com.message.utils"})
@MapperScan("com.message.mapper")
//@EntityScan("com.message.entity")
public class SystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(SystemApplication.class, args);
    }

}
