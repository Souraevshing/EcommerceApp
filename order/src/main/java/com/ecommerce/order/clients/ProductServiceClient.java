package com.ecommerce.order.clients;

import com.ecommerce.order.dto.ProductResponseDto;
import com.ecommerce.order.dto.ResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface ProductServiceClient {

    @GetExchange("/api/v1/products/{id}")
    ResponseDto<ProductResponseDto> getProductDetails(@PathVariable Long id);

}
