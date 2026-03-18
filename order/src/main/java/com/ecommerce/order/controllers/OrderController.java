package com.ecommerce.order.controllers;

import com.ecommerce.order.dto.OrderResponseDto;
import com.ecommerce.order.dto.ResponseDto;
import com.ecommerce.order.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestHeader("X-User-ID") Long userId) {
        log.info("Request received to create order");
        ResponseDto<OrderResponseDto> newOrder = orderService.createOrder(userId);

        if(newOrder.getError() != null) {
            log.warn("Failed to create order - {}", newOrder.getError());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(newOrder.getData());
        }

        log.info("Successfully created order");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newOrder.getData());
    }
}
