package com.ecommerce.app.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRoles role;
    private AddressDto address;
}
