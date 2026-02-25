package com.ecommerce.order.mappers;

import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.entities.CartItem;

public class CartItemMapper {

    public static CartItemResponseDto toDto(CartItem cartItem) {
        if (cartItem == null) return null;

        CartItemResponseDto dto = new CartItemResponseDto();
        dto.setId(cartItem.getId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setPrice(cartItem.getPrice());
        return dto;
    }
}