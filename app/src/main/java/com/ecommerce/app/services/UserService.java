package com.ecommerce.app.services;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.UserRequestDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.entities.Users;

import java.util.List;

public interface UserService {
    ResponseDto<UserResponseDto> createUser(UserRequestDto user);
    ResponseDto<Users> getUserById(Long id);
    ResponseDto<List<Users>> getAllUsers();
    ResponseDto<Users> updateUser(Users user, Long id);
    ResponseDto<String> deleteUser(Long id);
}
