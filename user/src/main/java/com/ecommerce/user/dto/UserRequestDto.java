package com.ecommerce.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "First name is mandatory")
    @Size(min = 3, message = "First name must be at least 3 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 3, message = "Last name must be at least 3 characters")
    private String lastName;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @NotNull(message = "Role is mandatory")
    private UserRoles role;

    @Valid
    @NotNull(message = "Address is mandatory")
    private AddressDto address;
}
