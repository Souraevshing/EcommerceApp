package com.ecommerce.app.controllers;

import com.ecommerce.app.dto.ProductRequestDto;
import com.ecommerce.app.dto.ProductResponseDto;
import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.services.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/products")
    public ResponseEntity<ResponseDto<ProductResponseDto>> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequestDto));
    }

    @PutMapping("/products/{id}")
    public  ResponseEntity<ResponseDto<ProductResponseDto>> updateProduct(@Valid @RequestBody ProductRequestDto productRequestDto, @PathVariable Long id) {
        ResponseDto<ProductResponseDto> response = productService.updateProduct(productRequestDto, id);

        if(response.getError() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
