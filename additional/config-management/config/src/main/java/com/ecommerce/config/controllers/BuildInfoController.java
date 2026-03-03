package com.ecommerce.config.controllers;

import com.ecommerce.config.services.BuildServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/config")
@AllArgsConstructor
public class BuildInfoController {
    private BuildServiceImpl buildService;

    @GetMapping("/build-info")
    public String getBuildInfo() {
        System.out.println("Build Info: " + buildService);
        return
                "Build Id: " + buildService.getBuildId()
                        + "\n Build Version: " + buildService.getBuildVersion()
                        + "\n Build Name: " + buildService.getBuildName();
    }
}
