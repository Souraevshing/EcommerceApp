package com.ecommerce.user.services;

import com.ecommerce.user.dto.ResponseDto;
import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {
    ResponseDto<UserResponseDto> createUser(UserRequestDto user);
    ResponseDto<UserResponseDto> getUserById(Long id);
    ResponseDto<List<UserResponseDto>> getAllUsers();
    ResponseDto<UserResponseDto> updateUser(UserRequestDto user, Long id);
    ResponseDto<String> deleteUser(Long id);
}
