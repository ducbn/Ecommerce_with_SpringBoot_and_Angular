package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.*;
import com.ducbn.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser (@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            userService.createUser(userDTO);
            return ResponseEntity.ok("Register successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        //kiểm tra thông tin đăng nhâoj và sinh token
        String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
        //trả về token trong respon

        return ResponseEntity.ok(token);
    }
}
