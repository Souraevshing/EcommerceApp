package com.ecommerce.app.services;

import com.ecommerce.app.dto.ProductRequestDto;
import com.ecommerce.app.dto.ProductResponseDto;
import com.ecommerce.app.dto.ResponseDto;

public interface ProductService {
    ResponseDto<ProductResponseDto> createProduct(ProductRequestDto productRequestDto);
    ResponseDto<ProductResponseDto> updateProduct(ProductRequestDto productRequestDto, Long id);
}
