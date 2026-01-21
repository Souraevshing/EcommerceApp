package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.entities.CartItem;

public class CartItemMapper {
    public static CartItemResponseDto toDto(CartItem cartItem) {
        CartItemResponseDto cartItemRequestDto = new CartItemResponseDto();
        cartItemRequestDto.setId(cartItem.getId());
        cartItemRequestDto.setProduct(ProductMapper.toDto(cartItem.getProduct()));
        cartItemRequestDto.setQuantity(cartItem.getQuantity());
        cartItemRequestDto.setUser(UserMapper.toDto(cartItem.getUser()));
        cartItemRequestDto.setPrice(cartItem.getPrice());
        return cartItemRequestDto;
    }
}
