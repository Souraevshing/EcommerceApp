package com.ecommerce.app.mapper;

import com.ecommerce.app.dto.AddressDto;
import com.ecommerce.app.dto.UserRequestDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.dto.UserRoles;
import com.ecommerce.app.entities.Address;
import com.ecommerce.app.entities.Users;

import java.util.List;

public class UserMapper {
    public static Users toEntity(UserRequestDto user) {
        Users users = new Users();
        users.setFirstName(user.getFirstName());
        users.setLastName(user.getLastName());
        users.setEmail(user.getEmail());
        users.setPhone(user.getPhone());
        users.setRole(user.getRole() != null ? user.getRole() : UserRoles.CUSTOMER);

        if(user.getAddress() != null) {
            Address address = new Address();
            address.setStreet(user.getAddress().getStreet());
            address.setCity(user.getAddress().getCity());
            address.setState(user.getAddress().getState());
            address.setZipcode(user.getAddress().getZipCode());
            address.setCountry(user.getAddress().getCountry());
            users.setAddress(address);
        }
        return users;
    }

    public static UserResponseDto toDto(Users user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipCode(user.getAddress().getZipcode());
            addressDto.setCountry(user.getAddress().getCountry());
            dto.setAddress(addressDto);
        }

        return dto;
    }

    public static List<UserResponseDto> toDtoList(List<Users> users) {
        return users
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }
}
