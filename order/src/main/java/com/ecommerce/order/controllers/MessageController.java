package com.ecommerce.order.controllers;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling message-related operations.
 * <p>
 * This class provides an endpoint to retrieve a configurable message,
 * with support for rate limiting and a fallback mechanism in case of errors.
 */
@RestController
@RefreshScope
public class MessageController {

    @Value("${app.message}")
    private String message;

    @GetMapping("/message")
    @RateLimiter(name = "product", fallbackMethod = "fallbackMessage")
    public String getMessage() {
        return message;
    }

    public String fallbackMessage(Throwable throwable) {
        return "Fallback message";
    }

}
