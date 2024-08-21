package com.alura.forum.controller;

import com.alura.forum.config.security.UserDetailsServiceImpl;
import com.alura.forum.model.dto.authentication.AuthLoginRequest;
import com.alura.forum.model.dto.authentication.AuthResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Authenticate via email and password",
            description = "Log in using user credentials to return a valid JSON Web Token"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to register a new topic",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AuthLoginRequest.class),
                    examples = @ExampleObject(
                            name = "Log in request",
                            summary = "Example of a request to log in and receive a JWT",
                            value = """
                                    {
                                        "username": "yuji_the_goat@email.com
                                        "password": "Sukumbia2024*
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Logged in successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(
                                    name = "Log in response",
                                    summary = "Example of a successful response",
                                    value = """
                                            {
                                                "username": "yuji_the_goat@email.com
                                                "password": "Sukumbia2024*
                                                "jwt": "ey454898..."
                                                "status": true
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Invalid credentials",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Bad credentials",
                                    summary = "Example of a response when credentials are invalid",
                                    value = """
                                            {
                                                "timestamp": "2024-08-19T23:04:40.011+00:00",
                                                "status": 403,
                                                "error": "Forbidden",
                                                "message": "Access Denied",
                                                "path": "/auth/login"
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest loginRequest){

        return new ResponseEntity<>(this.userDetailsService.loginUser(loginRequest), HttpStatus.OK);

    }

}
