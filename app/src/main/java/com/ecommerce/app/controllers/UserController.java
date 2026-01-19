package com.ecommerce.app.controllers;

import com.ecommerce.app.dto.ResponseDto;
import com.ecommerce.app.dto.UserRequestDto;
import com.ecommerce.app.dto.UserResponseDto;
import com.ecommerce.app.entities.Users;
import com.ecommerce.app.services.impl.UserServiceImpl;
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
    public ResponseEntity<ResponseDto<List<Users>>> getAllUsers(){
        ResponseDto<List<Users>> users = userService.getAllUsers();

        if(users.getData() == null || users.getData().isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(users);
        }

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDto<Users>> getUserById(@PathVariable Long id){
        ResponseDto<Users> response = userService.getUserById(id);
        if(response.getData() != null){
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseDto<UserResponseDto>> createUser(@RequestBody UserRequestDto user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDto<Users>> updateUser(@RequestBody Users user, @PathVariable Long id){
        ResponseDto<Users> response = userService.updateUser(user, id);

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
