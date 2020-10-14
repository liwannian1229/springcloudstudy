package com.lwn.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.lwn.service")
//表示当前服务为客户端服务
@EnableEurekaClient
@MapperScan(basePackages = "com.lwn.repo.mapper")
public class ServiceDemoApp {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDemoApp.class, args);
    }
    
}
