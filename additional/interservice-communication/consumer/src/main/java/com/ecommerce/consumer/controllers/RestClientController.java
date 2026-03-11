package com.ecommerce.consumer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/api/v1/rest-client")
public class RestClientController {

    @GetMapping("/instance")
    public String get() {
        RestClient restClient = RestClient.create();
        return restClient
                .get()
                .uri("http://localhost:9991/api/v1/instance")
                .retrieve()
                .body(String.class);
    }

}
