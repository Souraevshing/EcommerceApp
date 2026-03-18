package com.ecommerce.order.controllers;

import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.dto.ResponseDto;
import com.ecommerce.order.services.impl.CartItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartItemServiceImpl cartItemService;

    @PostMapping("/cart/add")
    public ResponseEntity<ResponseDto<CartItemResponseDto>> addToCart(@Valid @RequestBody CartItemRequestDto cartItemRequestDto, @RequestHeader("X-User-ID") Long userId){
        log.info("Request received to add product to cart");
        ResponseDto<CartItemResponseDto> response = cartItemService.addToCart(cartItemRequestDto, userId);

        if(response.getError() != null) {
            log.warn("Failed to add product to cart - {}", response.getError());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        log.info("Successfully added product to cart");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<ResponseDto<Void>> removeFromCart(@RequestHeader("X-User-ID") Long userId, @PathVariable Long productId) {
        log.info("Request received to remove product from cart");
        ResponseDto<Void> response = cartItemService.removeFromCart(productId, userId);

        if(response.getError() != null) {
            log.warn("Failed to remove product from cart - {}", response.getError());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        log.info("Successfully removed product from cart");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<ResponseDto<List<CartItemResponseDto>>> getCartItems(@RequestHeader("X-User-ID") Long userId) {
        log.info("Request received to fetch cart items");
        ResponseDto<List<CartItemResponseDto>> response = cartItemService.getCartItems(userId);

        if(response.getError() != null) {
            log.warn("Failed to fetch cart items - {}", response.getError());
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        log.info("Successfully fetched cart items");
        return ResponseEntity.ok(response);
    }
}
