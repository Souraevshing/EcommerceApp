package com.ecommerce.order.config;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.propagation.Propagator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

/***
 * Base configuration for RestClient so that each service can use the same RestClient.Builder instance
 */

@Configuration
public class RestClientBaseConfig {

    @Autowired(required = false)
    private ObservationRegistry observationRegistry;

    @Autowired(required = false)
    private Tracer tracer;

    @Autowired(required = false)
    private Propagator propagator;

    @Bean
    @Primary
    public RestClient.Builder restClientBuilder() {
        RestClient.Builder builder = RestClient.builder();

        if(observationRegistry != null) {
            builder.requestInterceptor(createTracingInterceptor());
        }

        return builder;
    }

    @Bean
    @LoadBalanced
    @Qualifier("lbRestClientBuilder")
    public RestClient.Builder lbRestClientBuilder() {
        RestClient.Builder builder = RestClient.builder();

        if(observationRegistry != null) {
            builder.requestInterceptor(createTracingInterceptor());
        }

        return builder;
    }

    private ClientHttpRequestInterceptor createTracingInterceptor() {
        return ((req, body, execution) -> {
            if(tracer != null && propagator != null && tracer.currentSpan() != null) {
                propagator.inject(tracer.currentTraceContext().context(),
                        req.getHeaders(),
                        (carrier, key, value) -> {
                            assert carrier != null;
                            carrier.add(key, value);
                        });
            }
            return execution.execute(req, body);
        });
    }

}
