package com.ecommerce.app.services;

import com.ecommerce.app.dto.CartItemRequestDto;
import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.dto.ResponseDto;

public interface CartItemService {
    ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId);
}
