package com.ecommerce.app.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private String message;
    private T data;
    private String error;

    public static <T> ResponseDto<T> success(T data, String message) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        responseDto.setMessage(message);
        return responseDto;
    }

    public static <T> ResponseDto<T> error(T data, String error) {
        ResponseDto<T> responseDto = new ResponseDto<>();
        responseDto.setData(data);
        responseDto.setError(error);
        return responseDto;
    }
}
