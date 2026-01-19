package com.ecommerce.app.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserResponseDto {

    @NotNull
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @NotNull
    private UserRoles role;

    @Valid
    private AddressDto address;
}
