//package com.spring.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.beans.factory.annotation.Autowired;
//
//@Configuration
//public class RequestInterceptorConfig implements WebMvcConfigurer {
//
//    @Autowired
//    private AuthTokenInterceptor authTokenInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authTokenInterceptor)
//                .addPathPatterns("/api/v1/user")
//                .addPathPatterns("/api/v1/user/view/coupons")
//                .addPathPatterns("/api/v1/user/view/notifications")
//                .addPathPatterns("/api/v1/user/booking/**")
//                .excludePathPatterns("/api/v1/auth/**", "/api/v1/logout");
//    }
//}
//
