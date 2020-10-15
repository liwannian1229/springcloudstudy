package com.lwn.demo.service.configurer;

import com.lwn.common.model.configuration.GlobalWebMvcConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@ControllerAdvice
@MapperScan("com.lwn.repo.mapper")
public class GlobalWebMvcConfiguration extends GlobalWebMvcConfig {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
