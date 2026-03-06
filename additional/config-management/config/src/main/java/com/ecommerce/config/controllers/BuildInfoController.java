package com.ecommerce.config.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/api/v1/config")
public class BuildInfoController {
    @Value("${build.id:default}")
    private String buildId;

    @Value("${build.version:default}")
    private String buildVersion;

    @Value("${build.name:default}")
    private String buildName;

    @Value("${build.type:default}")
    private String buildType;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        return
                "Build Id: " + buildId + ",\n"
                        + "Build Version: " + buildVersion + ",\n"
                        + "Build Name: " + buildName+ ",\n"
                        + "Build Type: " + buildType + "\n";
    }
}
