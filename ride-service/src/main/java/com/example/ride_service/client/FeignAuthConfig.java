package com.example.ride_service.client;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignAuthConfig {

    @Bean
    public RequestInterceptor authForwardingInterceptor() {
        return requestTemplate -> {
            var attributes = RequestContextHolder.getRequestAttributes();
            if (attributes instanceof ServletRequestAttributes servletAttributes) {
                String authHeader = servletAttributes.getRequest().getHeader("Authorization");
                if (authHeader != null) {
                    requestTemplate.header("Authorization", authHeader);
                }
            }
        };
    }
}
