package com.shreyas.pom.controller;

import com.shreyas.pom.Exception.UserException;
import com.shreyas.pom.payload.dto.UserDto;
import com.shreyas.pom.payload.responce.AuthResponce;
import com.shreyas.pom.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<AuthResponce> signupHandler(
            @RequestBody UserDto userDto
            ) throws UserException {
      return ResponseEntity.ok(authService.signup(userDto));

    }



    @PostMapping("/login")
    public ResponseEntity<AuthResponce> loginHandler(
            @RequestBody UserDto userDto
    ) throws UserException {
        return ResponseEntity.ok(authService.login(userDto));

    }




}
