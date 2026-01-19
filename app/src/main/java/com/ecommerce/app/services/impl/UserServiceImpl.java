package com.ecommerce.app.services.impl;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.UserRequestDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.entities.Users;
import com.ecommerce.app.mapper.UserMapper;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ResponseDto<UserResponseDto> createUser(UserRequestDto userDto) {
        Users user = UserMapper.toEntity(userDto);
        Users savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = UserMapper.toDto(savedUser);
        return ResponseDto.success(
                userResponseDto,
                "User created successfully"
        );
    }

    @Override
    public ResponseDto<UserResponseDto> getUserById(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()) {
            return ResponseDto.error(null, "User not found");
        }
        return ResponseDto.success(
                UserMapper.toDto(user.get()),
                "User fetched successfully"
        );
    }

    @Override
    public ResponseDto<List<Users>> getAllUsers() {
        List<Users> users = userRepository.findAll();
        if(users.isEmpty()) {
            return ResponseDto.error(
                    null,
                    "No users found"
            );
        }
        return ResponseDto.success(
                users,
                "All users fetched successfully"
        );
    }

    @Override
    public ResponseDto<Users> updateUser(Users user, Long id) {
        Optional<Users> users = userRepository.findById(id);
        if(users.isEmpty()) {
            return ResponseDto.error(null, "User not found");
        }
        Users updatedUser = new Users();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        userRepository.save(updatedUser);
        return ResponseDto.success(updatedUser, "User updated successfully");
    }

    @Override
    public ResponseDto<String> deleteUser(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if(users.isEmpty()) {
            return ResponseDto.error(null, "User not found");
        }
        userRepository.deleteById(id);
        return ResponseDto.success(null, "User deleted successfully");
    }

}
