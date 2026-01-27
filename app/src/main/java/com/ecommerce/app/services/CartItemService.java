package com.ecommerce.app.services;

import com.ecommerce.app.dto.CartItemRequestDto;
import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.dto.ResponseDto;

import java.util.List;

public interface CartItemService {
    ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId);
    ResponseDto<String> removeFromCart(Long productId, Long userId);
    ResponseDto<List<CartItemResponseDto>> getCartItems(Long userId);
}
