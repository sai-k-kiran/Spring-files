package com.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;

    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Override
    public void addCorsMappings(CorsRegistry registry){
        CorsRegistration registration = registry.addMapping("/api/**");
        allowedOrigins.forEach(origin -> registration.allowedOrigins(origin));
        allowedOrigins.forEach(origin -> registration.allowedMethods(origin));
    }
}
