package com.example.ride_matching_service.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    // Plain, non-load-balanced builder. @Primary so that any code which
    // autowires RestClient.Builder WITHOUT a qualifier -- including Spring
    // Cloud Netflix's internal Eureka HTTP client, which talks to the Eureka
    // server itself -- resolves to this one instead of the load-balanced bean
    // below. Without this, our load-balanced bean was the only RestClient.Builder
    // in the context, so Eureka's client picked it up by default (a @Qualifier
    // on a bean definition doesn't stop an unqualified injection point from
    // using it), routing Eureka's own register/heartbeat calls through the
    // load balancer -- which then tried to resolve "localhost" as a service id
    // and failed with "No instances available for localhost".
    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    // Only for calling other Eureka-registered services (driver-service,
    // ride-service). Must be requested explicitly via @Qualifier("loadBalanced").
    @Bean
    @LoadBalanced
    @Qualifier("loadBalanced")
    public RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }
}