package com.ecommerce.order.config;

import com.ecommerce.order.clients.UserServiceClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserServiceClientConfig {

    @Bean
    @LoadBalanced
    public RestClient.Builder userRestClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public UserServiceClient userServiceClientInterface(
            @Qualifier("lbRestClientBuilder") RestClient.Builder restClientBuilder) {

        RestClient restClient = restClientBuilder
                .baseUrl("http://user")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError, (req, res) -> {
                    System.out.println(res);
                })
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(UserServiceClient.class);
    }
}
