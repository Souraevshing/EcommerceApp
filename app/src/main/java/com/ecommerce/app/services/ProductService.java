package com.ecommerce.app.services;

import com.ecommerce.app.dto.ProductRequestDto;
import com.ecommerce.app.dto.ProductResponseDto;
import com.ecommerce.app.dto.ResponseDto;

import java.util.List;

public interface ProductService {
    ResponseDto<ProductResponseDto> createProduct(ProductRequestDto productRequestDto);
    ResponseDto<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, Long id);
    ResponseDto<ProductResponseDto> getProductById(Long id);
    ResponseDto<String> deleteProduct(Long id);
    ResponseDto<List<ProductResponseDto>> getAllProducts();
    ResponseDto<List<ProductResponseDto>> searchProductByName(String name);
}
