package com.ecommerce.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route("product", r -> r
                        .path("/api/v1/products","/api/v1/products/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("ecommerceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/products"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .setMethods(HttpMethod.GET)
                                ))
                        .uri("lb://PRODUCT"))
                .route("user", r -> r
                        .path("/api/v1/users","/api/v1/users/**","/api/v1/auth","/api/v1/auth/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("ecommerceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/user"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .setMethods(HttpMethod.GET)
                                ))
                        .uri("lb://USER"))
                .route("order", r -> r
                        .path("/api/v1/order","/api/v1/order/**","/api/v1/cart","/api/v1/cart/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("ecommerceCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/order"))
                                .retry(config -> config
                                        .setRetries(3)
                                        .setStatuses(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .setMethods(HttpMethod.GET)
                                ))
                        .uri("lb://ORDER"))
                .route("eureka-static", r -> r
                        .path("/eureka/web")
                        .filters(f -> f.setPath("/"))
                        .uri("http://localhost:8761"))
                .route("eureka-resources", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }

}
