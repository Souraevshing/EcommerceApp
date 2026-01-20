package com.ecommerce.app.services.impl;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.UserRequestDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.entities.Users;
import com.ecommerce.app.mapper.UserMapper;
import com.ecommerce.app.repository.UserRepository;
import com.ecommerce.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public ResponseDto<UserResponseDto> createUser(UserRequestDto userRequestDto) {
        Users savedUser = userRepository.save(UserMapper.toEntity(userRequestDto));
        return ResponseDto.success(
                UserMapper.toDto(savedUser),
                "User created successfully"
        );
    }

    @Override
    public ResponseDto<UserResponseDto> getUserById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> ResponseDto.success(
                        UserMapper.toDto(user),
                        "User fetched successfully"
                ))
                .orElse(ResponseDto.error("User not found"));
    }

    @Override
    public ResponseDto<List<UserResponseDto>> getAllUsers() {
        List<Users> users = userRepository.findAll();

        if(users.isEmpty()) {
            return ResponseDto.error(
                    "No users found"
            );
        }

        return ResponseDto.success(
                UserMapper.toDtoList(users),
                "All users fetched successfully"
        );
    }

    @Override
    public ResponseDto<UserResponseDto> updateUser(UserRequestDto userRequestDto, Long id) {
        Users users = UserMapper.toEntity(userRequestDto);
        Optional<Users> userFound = userRepository.findById(id);
        if(userFound.isEmpty()) {
            return ResponseDto.error("User not found");
        }
        users.setFirstName(userRequestDto.getFirstName());
        users.setLastName(userRequestDto.getLastName());
        Users savedUser = userRepository.save(users);
        UserResponseDto updatedUser = UserMapper.toDto(savedUser);
        return ResponseDto.success(updatedUser, "User updated successfully");
    }

    @Override
    public ResponseDto<String> deleteUser(Long id) {
        Optional<Users> users = userRepository.findById(id);
        if(users.isEmpty()) {
            return ResponseDto.error("User not found");
        }
        userRepository.deleteById(id);
        return ResponseDto.success("Deleted", "User deleted successfully");
    }
}
