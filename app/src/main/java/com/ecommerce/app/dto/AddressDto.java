package com.ecommerce.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    @NotBlank(message = "Street is mandatory")
    @Size(min = 3, message = "Street must be at least 3 characters")
    private String street;

    @NotBlank(message = "City is mandatory")
    @Size(min = 3, message = "City must be at least 3 characters")
    private String city;

    @NotBlank(message = "State is mandatory")
    @Size(min = 2, message = "State must be at least 2 characters")
    private String state;

    @NotBlank(message = "Zip code is mandatory")
    @Pattern(regexp = "^[0-9]{6}$", message = "Zip code must be 6 digits")
    private String zipCode;

    @NotBlank(message = "Country is mandatory")
    @Size(min = 3, message = "Country must be at least 3 characters")
    private String country;
}
