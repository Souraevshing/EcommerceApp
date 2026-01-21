package com.ecommerce.app.controllers;

import com.ecommerce.app.dto.CartItemRequestDto;
import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.services.impl.CartItemServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class CartController {
    private final CartItemServiceImpl cartItemService;

    @PostMapping("/cart")
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
}
