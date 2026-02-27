package com.ecommerce.config.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
@RequiredArgsConstructor
public class BuildInfoController {
    private String buildId;
    private String buildVersion;
    private String buildName;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return "Build Id: " + buildId + ", Build Version: " + buildVersion + ", Build Name: " + buildName;
    }
}
