package com.ecommerce.app.controllers;

import com.ecommerce.app.dto.OrderResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.services.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestHeader("X-User-ID") Long userId) {
        ResponseDto<OrderResponseDto> newOrder = orderService.createOrder(userId);

        if(newOrder.getError() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(newOrder.getData());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newOrder.getData());
    }
}
