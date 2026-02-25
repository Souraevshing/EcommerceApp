package com.ecommerce.order.services.impl;

import com.ecommerce.order.dto.CartItemRequestDto;
import com.ecommerce.order.dto.CartItemResponseDto;
import com.ecommerce.order.dto.ResponseDto;
import com.ecommerce.order.entities.CartItem;
import com.ecommerce.order.mappers.CartItemMapper;
import com.ecommerce.order.repositories.CartItemRepository;
import com.ecommerce.order.services.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;

    @Override
    public ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId) {
        CartItem existingItems = cartItemRepository.findByUserIdAndProductId(userId, cartItemRequestDto.getProductId());
        if(existingItems != null) {
            existingItems.setQuantity(existingItems.getQuantity());
            existingItems.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingItems);
            return ResponseDto.success(CartItemMapper.toDto(existingItems), "Cart item updated successfully");
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequestDto.getProductId());
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);
            return ResponseDto.success(CartItemMapper.toDto(cartItem), "Cart item added successfully");
        }
    }

    @Override
    public ResponseDto<Void> removeFromCart(Long productId, Long userId) {
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);

        if(cartItem == null) {
            return ResponseDto.error("Cart item not found");
        }

        cartItemRepository.delete(cartItem);
        return ResponseDto.success("Cart item removed successfully");
    }

    @Override
    public ResponseDto<List<CartItemResponseDto>> getCartItems(Long userId) {
        List<CartItem> items =  cartItemRepository.findByUserId(userId);

        if(items.isEmpty()) {
            return ResponseDto.error("Cart is empty");
        }

        return ResponseDto.success(items.stream().map(CartItemMapper::toDto).toList(), "Cart items fetched successfully");
    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
        ResponseDto.success("Cart cleared successfully", null);
    }
}
