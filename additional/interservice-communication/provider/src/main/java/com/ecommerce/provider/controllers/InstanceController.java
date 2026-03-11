package com.ecommerce.provider.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class InstanceController {
    @Value("${server.port}")
    private String port;
    private final String instanceId = java.util.UUID.randomUUID().toString();

    @GetMapping("/instance")
    public String getInstance() {
        return "Server is running on port: " + port + " and instance id: " + instanceId + "\n";
    }
}
