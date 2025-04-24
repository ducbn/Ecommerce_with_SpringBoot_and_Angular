package com.ducbn.shopapp.controllers;

import com.ducbn.shopapp.dtos.*;
import com.ducbn.shopapp.models.User;
import com.ducbn.shopapp.responses.LoginResponse;
import com.ducbn.shopapp.responses.RegisterResponse;
import com.ducbn.shopapp.services.UserService;
import com.ducbn.shopapp.components.LocalizationUtils;
import com.ducbn.shopapp.utils.MessageKey;
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
    private final LocalizationUtils localizationUtils;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> createUser (@Valid @RequestBody UserDTO userDTO, BindingResult result) {
        try{
            if (result.hasErrors()) {
                List<String> errorMessages = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(
                        RegisterResponse.builder()
                                .message(localizationUtils.getLocalizedMessage(MessageKey.REGISTER_FAILED, errorMessages))
                                .build()
                );
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body(
                        RegisterResponse.builder()
                                .user(null)
                                .message(localizationUtils.getLocalizedMessage(MessageKey.PASSWORD_NOT_MATCH))
                                .build()
                );
            }
            User user = userService.createUser(userDTO);
            return ResponseEntity.ok(
                    RegisterResponse.builder()
                            .user(user)
                            .message(localizationUtils.getLocalizedMessage(MessageKey.REGISTER_SUCCESSFULLY))
                            .build()
            );
        }catch (Exception e){
            return ResponseEntity.badRequest().body(
                    RegisterResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKey.REGISTER_FAILED, e.getMessage()))
                            .build()
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserLoginDTO userLoginDTO)
    {
        //kiểm tra thông tin đăng nhâo và sinh token
        try {
            String token = userService.login(
                    userLoginDTO.getPhoneNumber(),
                    userLoginDTO.getPassword(),
                    userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
            );

            return ResponseEntity.ok(LoginResponse.builder()
                    .token(token)
                    .message(localizationUtils.getLocalizedMessage(MessageKey.LOGIN_SUCCESSFULLY))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message(localizationUtils.getLocalizedMessage(MessageKey.LOGIN_FAILED, e.getMessage()))
                            .build()
            );
        }
    }
}
