package com.CrabClawsApplication.config;

import com.CrabClawsApplication.Security.LoginCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration //配置类
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**")// 拦截器/**
                .excludePathPatterns("/login","/swagger-ui/**","/v3/**","/files/**","/SSRF/**","/druid/**","/actuator/**","/actuator");

    }
}
