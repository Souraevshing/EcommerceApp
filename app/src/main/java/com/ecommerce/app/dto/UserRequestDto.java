package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AddressDto address;
}
