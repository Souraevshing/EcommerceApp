package com.ecommerce.user.mappers;

import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.dto.UserRoles;
import com.ecommerce.user.entities.Address;
import com.ecommerce.user.entities.Users;

import java.util.List;

public class UserMapper {
    public static Users toEntity(UserRequestDto userRequestDto) {
        Users users = new Users();
        users.setFirstName(userRequestDto.getFirstName());
        users.setLastName(userRequestDto.getLastName());
        users.setEmail(userRequestDto.getEmail());
        users.setPhone(userRequestDto.getPhone());
        users.setRole(userRequestDto.getRole() != null ? userRequestDto.getRole() : UserRoles.CUSTOMER);

        if(userRequestDto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequestDto.getAddress().getStreet());
            address.setCity(userRequestDto.getAddress().getCity());
            address.setState(userRequestDto.getAddress().getState());
            address.setZipcode(userRequestDto.getAddress().getZipCode());
            address.setCountry(userRequestDto.getAddress().getCountry());
            users.setAddress(address);
        }
        return users;
    }

    public static UserResponseDto toDto(Users user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(Long.valueOf(user.getId()));
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setRole(user.getRole());

        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipCode(user.getAddress().getZipcode());
            addressDto.setCountry(user.getAddress().getCountry());
            userResponseDto.setAddress(addressDto);
        }

        return userResponseDto;
    }

    public static Address toAddress(AddressDto dto) {
        if (dto == null) return null;

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipcode(dto.getZipCode());
        address.setCountry(dto.getCountry());
        return address;
    }

    public static void  updateEntity(Users user, UserRequestDto userRequestDto) {
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setRole(userRequestDto.getRole());
        user.setAddress(toAddress(userRequestDto.getAddress()));
    }

    public static List<UserResponseDto> toDtoList(List<Users> users) {
        return users
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }
}
