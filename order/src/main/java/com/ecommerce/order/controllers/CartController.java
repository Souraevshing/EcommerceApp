package com.ecommerce.order.controllers;

import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.dto.ResponseDto;
import com.ecommerce.order.services.impl.CartItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CartController {
    private final CartItemServiceImpl cartItemService;

    @PostMapping("/cart/add")
    public ResponseEntity<ResponseDto<CartItemResponseDto>> addToCart(@Valid @RequestBody CartItemRequestDto cartItemRequestDto, @RequestHeader("X-User-ID") Long userId){
        ResponseDto<CartItemResponseDto> response = cartItemService.addToCart(cartItemRequestDto, userId);

        if(response.getError() != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<ResponseDto<Void>> removeFromCart(@RequestHeader("X-User-ID") Long userId, @PathVariable Long productId) {
        ResponseDto<Void> response = cartItemService.removeFromCart(productId, userId);
        if(response.getError() != null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cart/items")
    public ResponseEntity<ResponseDto<List<CartItemResponseDto>>> getCartItems(@RequestHeader("X-User-ID") Long userId) {
        ResponseDto<List<CartItemResponseDto>> response = cartItemService.getCartItems(userId);
        if(response.getError() != null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(response);
    }
}
