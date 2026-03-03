package com.ecommerce.config.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "build")
@Data
@RefreshScope
public class BuildServiceImpl {
    private String buildId;
    private String buildVersion;
    private String buildName;
}
