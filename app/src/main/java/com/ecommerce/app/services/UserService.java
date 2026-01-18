package com.ecommerce.app.services;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.entities.Users;

import java.util.List;

public interface UserService {
    ResponseDto<Users> createUser(Users user);
    ResponseDto<Users> getUserById(Long id);
    ResponseDto<List<Users>> getAllUsers();
    ResponseDto<Users> updateUser(Users user, Long id);
    ResponseDto<String> deleteUser(Long id);
}
