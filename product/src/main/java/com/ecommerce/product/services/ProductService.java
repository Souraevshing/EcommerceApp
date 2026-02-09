package com.ecommerce.product.services;

import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.dto.ResponseDto;

import java.util.List;

public interface ProductService {
    ResponseDto<ProductResponseDto> createProduct(ProductRequestDto productRequestDto);
    ResponseDto<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, Long id);
    ResponseDto<ProductResponseDto> getProductById(Long id);
    ResponseDto<String> deleteProduct(Long id);
    ResponseDto<List<ProductResponseDto>> getAllProducts();
    ResponseDto<List<ProductResponseDto>> searchProductByName(String name);
}
