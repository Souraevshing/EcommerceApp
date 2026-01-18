package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.AddressDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.entities.Users;

public class UserMapper {

    public static UserResponseDto toEntity(Users user) {
        UserResponseDto users = new UserResponseDto();
        users.setId(user.getId().toString());
        users.setFirstName(user.getFirstName());
        users.setLastName(user.getLastName());
        users.setEmail(user.getEmail());
        users.setPhone(user.getPhone());
        users.setRole(user.getRole());

        if(user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipcode(user.getAddress().getZipcode());
            addressDto.setCountry(user.getAddress().getCountry());
            users.setAddress(addressDto);
        }
        return users;
    }
}
