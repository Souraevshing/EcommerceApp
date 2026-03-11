package com.ecommerce.consumer.services;

import com.ecommerce.consumer.config.RestTemplateConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestTemplateService {
    private final RestTemplateConfig restTemplateConfig;
    private static final String PROVIDER_BASE_URL = "http://localhost:9991";

    public String getInstance() {
        return restTemplateConfig.restTemplate().getForObject(PROVIDER_BASE_URL + "/api/v1/instance", String.class);
    }
}
