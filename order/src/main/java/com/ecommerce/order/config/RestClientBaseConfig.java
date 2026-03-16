package com.ecommerce.order.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;

/***
 * Base configuration for RestClient so that each service can use the same RestClient.Builder instance
 */

@Configuration
public class RestClientBaseConfig {

    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    @LoadBalanced
    @Qualifier("lbRestClientBuilder")
    public RestClient.Builder lbRestClientBuilder() {
        return RestClient.builder();
    }

}
