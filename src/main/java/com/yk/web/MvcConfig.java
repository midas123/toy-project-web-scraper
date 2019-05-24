package com.yk.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	String in_path = "classpath:/static/"; //프로젝트 내부
    	//String ex_path = "file:///C:/Users/yk1/Desktop/upload2/images/"; //외부
       registry
               .addResourceHandler("/**").addResourceLocations(in_path);
    }
} 