package com.alura.forum.controller;

import com.alura.forum.config.security.UserDetailsServiceImpl;
import com.alura.forum.model.dto.authentication.AuthLoginRequest;
import com.alura.forum.model.dto.authentication.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")

public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest loginRequest){

        return new ResponseEntity<>(this.userDetailsService.loginUser(loginRequest), HttpStatus.OK);

    }

}
