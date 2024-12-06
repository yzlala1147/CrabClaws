//package com.CrabClawsApplication.config;
//
//import org.springdoc.core.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SwaggerConfig {
//
//    @Bean
//    public GroupedOpenApi publicApi() {
//        return GroupedOpenApi.builder()
//                .group("")
//                .pathsToMatch("/api/**")  // 指定要生成文档的接口路径匹配规则，可按需调整
//                .build();
//    }
//}