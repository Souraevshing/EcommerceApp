package com.ecommerce.order.services;

import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.dto.ResponseDto;

import java.util.List;

public interface CartItemService {
    ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId);
    ResponseDto<Void> removeFromCart(Long productId, Long userId);
    ResponseDto<List<CartItemResponseDto>> getCartItems(Long userId);
    void clearCart(Long userId);
}
