package com.ecommerce.config.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class BuildInfoController {
    @Value("${build.id:default_id}")
    private String buildId;

    @Value("${build.version:0.0.1}")
    private String buildVersion;

    @Value("${build.name:Config-Build}")
    private String buildName;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return
                "Build Id: " + buildId
                        + "\n Build Version: " + buildVersion
                        + "\n Build Name: " + buildName;
    }
}
