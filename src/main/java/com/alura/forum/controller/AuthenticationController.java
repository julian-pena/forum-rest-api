package com.alura.forum.controller;

import com.alura.forum.config.security.UserDetailsServiceImpl;
import com.alura.forum.model.dto.authentication.AuthLoginRequest;
import com.alura.forum.model.dto.authentication.AuthResponse;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Operations for authentication")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Operation(
            summary = "Authenticate a user",
            description = "Authenticate an existing user using email and password and receive a JSON web token back",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User Authentication data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthLoginRequest.class),
                            examples = @ExampleObject(
                                    name = "User Authentication Example",
                                    summary = "Example of user authentication",
                                    value = """
                                            {
                                                "username": "jane.doe@example.com",
                                                "password": "SecurePass!123"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged in successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class),
                            examples = @ExampleObject(
                                    name = "Successful User Response",
                                    summary = "Example of a successful user creation",
                                    value = """
                                           {
                                                "username": "yuji_the_goat@email.com",
                                                "password": "Sukumbia2024*",
                                                "jwt": "ey454898...",
                                                "status": "true"
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
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Missing username field",
                                    summary = "Example of a response when body is invalid",
                                    value = """
                                            {
                                              "timeStamp": "2024-08-30 10:55:57",
                                              "username": "no debe estar vacío",
                                              "status": 400
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
