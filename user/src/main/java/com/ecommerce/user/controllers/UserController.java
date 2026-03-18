package com.ecommerce.user.controllers;

import com.ecommerce.user.dto.ResponseDto;
import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAllUsers(){
        log.info("Request received to fetch all users");
        ResponseDto<List<UserResponseDto>> users = userService.getAllUsers();

        if(users.getData() == null || users.getData().isEmpty()){
            log.warn("No users found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
        }

        log.info("Successfully fetched {} users",users.getData().size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserById(@PathVariable Long id){
        log.info("Request received to fetch user with id {}",id);
        ResponseDto<UserResponseDto> response = userService.getUserById(id);

        if(response.getData() != null){
            log.info("Successfully fetched user with id: {}", id);
            return ResponseEntity.ok(response);
        }

        log.warn("User not found with id: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        log.info("Request received to create user");
        ResponseDto<UserResponseDto> response = userService.createUser(userRequestDto);
        log.info("Successfully created user with id: {}", response.getData().getId());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id){
        log.info("Request received to update user with id {}", id);
        ResponseDto<UserResponseDto> response = userService.updateUser(userRequestDto, id);

        if (response.getError() != null) {
            log.warn("Failed to update user with id: {} - {}", id, response.getError());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        log.info("Successfully updated user with id: {}", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDto<Void>> deleteUser(@PathVariable Long id){
        log.info("Request received to delete user with id {}", id);
        ResponseDto<Void> response = userService.deleteUser(id);

        if (response.getError() != null) {
            log.warn("Failed to delete user with id: {} - {}", id, response.getError());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        log.info("Successfully deleted user with id: {}", id);
        return ResponseEntity.ok(response);
    }
}
