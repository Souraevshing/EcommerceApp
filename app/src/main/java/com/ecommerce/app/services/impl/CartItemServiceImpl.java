package com.ecommerce.app.services.impl;

import com.ecommerce.app.dto.CartItemRequestDto;
import com.ecommerce.app.dto.CartItemResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.entities.CartItem;
import com.ecommerce.app.entities.Product;
import com.ecommerce.app.entities.Users;
import com.ecommerce.app.mapper.CartItemMapper;
import com.ecommerce.app.repository.CartItemRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.CartItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<CartItemResponseDto> addToCart(CartItemRequestDto cartItemRequestDto, Long userId) {
        Optional<Product> productFound = productRepository.findById(cartItemRequestDto.getProductId());
        if(productFound.isEmpty()) {
            return ResponseDto.error("Product not found");
        }

        Product product = productFound.get();
        if(product.getStockQuantity() < cartItemRequestDto.getQuantity()) {
            return ResponseDto.error("Out of stock");
        }

        Optional<Users> userFound = userRepository.findById(userId);
        if(userFound.isEmpty()) {
            return ResponseDto.error("User not found");
        }

        Users users = userFound.get();
        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(users, product);
        if(existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequestDto.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            CartItem updatedCartItems = cartItemRepository.save(existingCartItem);
            return ResponseDto.success(CartItemMapper.toDto(updatedCartItems), "Cart updated successfully");
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(users);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequestDto.getQuantity())));
            CartItem savedCartItems = cartItemRepository.save(cartItem);
            return ResponseDto.success(CartItemMapper.toDto(savedCartItems), "Cart item added successfully");
        }
    }

    @Override
    public ResponseDto<String> removeFromCart(Long productId, Long userId) {
        Users users = userRepository.findById(userId).orElse(null);
        if(users == null) {
            return ResponseDto.error("User not found");
        }

        Product product = productRepository.findById(productId).orElse(null);
        if(product == null) {
            return ResponseDto.error("Product not found");
        }

        CartItem cartItem = cartItemRepository.findByUserAndProduct(users, product);
        if(cartItem == null) {
            return ResponseDto.error("Cart item not found");
        }

        cartItemRepository.delete(cartItem);

        return ResponseDto.success("Cart item removed successfully", null);
    }
}
