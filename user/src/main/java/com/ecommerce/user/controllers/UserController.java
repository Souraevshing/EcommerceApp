package com.ecommerce.user.controllers;

import com.ecommerce.user.dto.ResponseDto;
import com.ecommerce.user.dto.UserRequestDto;
import com.ecommerce.user.dto.UserResponseDto;
import com.ecommerce.user.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/users")
    public ResponseEntity<ResponseDto<List<UserResponseDto>>> getAllUsers(){
        ResponseDto<List<UserResponseDto>> users = userService.getAllUsers();

        if(users.getData() == null || users.getData().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> getUserById(@PathVariable Long id){
        ResponseDto<UserResponseDto> response = userService.getUserById(id);
        if(response.getData() != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDto<UserResponseDto>> createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userRequestDto));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDto<UserResponseDto>> updateUser(@Valid @RequestBody UserRequestDto userRequestDto, @PathVariable Long id){
        ResponseDto<UserResponseDto> response = userService.updateUser(userRequestDto, id);

        if (response.getError() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseDto<String>> deleteUser(@PathVariable Long id){
        ResponseDto<String> response = userService.deleteUser(id);

        if (response.getError() != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        return ResponseEntity.ok(response);
    }
}
