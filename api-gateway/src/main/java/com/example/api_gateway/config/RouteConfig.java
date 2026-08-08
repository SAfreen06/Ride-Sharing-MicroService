package com.example.api_gateway.config;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.stripPrefix;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class RouteConfig {

    // Each teammate adds their own route bean here once their service is
    // registered with Eureka under spring.application.name, e.g. "rider-service".

    @Bean
    public RouterFunction<ServerResponse> authServiceRoute() {
        return route("auth-service")
                .route(path("/api/auth/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("auth-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> riderServiceRoute() {
        return route("rider-service")
                .route(path("/api/riders/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("rider-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> driverServiceRoute() {
        return route("driver-service")
                .route(path("/api/drivers/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("driver-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> rideMatchingServiceRoute() {
        return route("ride-matching-service")
                .route(path("/api/rides/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("ride-matching-service"))
                .build();
    }
  
    @Bean
    public RouterFunction<ServerResponse> rideServiceRoute() {
        return route("ride-service")
                .route(path("/api/rides/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("ride-service"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> notificationServiceRoute() {
        return route("notification-service")
                .route(path("/api/notifications/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("notification-service"))
                .build();
    }
  
    @Bean
    public RouterFunction<ServerResponse> fareServiceRoute() {
        return route("fare-service")
                .route(path("/api/fares/**"), http())
                .filter(stripPrefix(1))
                .filter(lb("fare-service"))
                .build();
    }

}
