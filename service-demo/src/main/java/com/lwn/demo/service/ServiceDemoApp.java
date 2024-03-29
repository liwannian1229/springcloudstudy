package com.lwn.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(scanBasePackages = "com.lwn.demo.service")
//表示当前服务为客户端服务
@EnableEurekaClient
@MapperScan(basePackages = "com.lwn.repo.mapper")
@EnableRedisHttpSession
public class ServiceDemoApp {

    public static void main(String[] args) {
        SpringApplication.run(ServiceDemoApp.class, args);
    }

}
