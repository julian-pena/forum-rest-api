package com.alura.forum.controller;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operations about users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @Operation(summary = "Get all users",
            description = "Returns a pageable list of all users",
            parameters = {
                    @Parameter(name = "page", description = "Page number", example = "0", required = false),
                    @Parameter(name = "size", description = "Size of the page", example = "10", required = false),
                    @Parameter(name = "sort", description = "Sort criteria", example = "registrationDate,asc", required = false)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = Page.class),
                                    examples = @ExampleObject(
                                            name = "Successful Users Page Response",
                                            summary = "Example of a successful users page response",
                                            value = "{ \"content\": [{ \"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\" }], \"totalPages\": 1, \"totalElements\": 1 }"
                                    ))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid or missing authentication token",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Unauthorized Example",
                                            summary = "Example of an unauthorized request due to missing or invalid token",
                                            value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    @Transactional(readOnly = true)
    @GetMapping
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<Page<UserInfoDTO>> getUsers(@PageableDefault(sort = "registrationDate", direction = Sort.Direction.ASC, size = 20) Pageable pageable){
        Page<UserInfoDTO> usersInDatabase = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersInDatabase);
    }



    @Operation(summary = "Get user by ID",
            description = "Returns a User by their ID",
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to retrieve", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserInfoDTO.class),
                                    examples = @ExampleObject(
                                            name = "Successful User Response",
                                            summary = "A successful response example",
                                            value = "{ \"id\": 1, \"name\": \"John Doe\", \"email\": \"john.doe@example.com\" }"
                                    ))),
                    @ApiResponse(responseCode = "404", description = "User not found",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "User Not Found Response",
                                            summary = "Example of user not found response",
                                            value = """
                                                    {   "timeStamp": "2024-08-16 09:46:57",
                                                        "message": "User not found with id: 15",
                                                        "status": 404
                                                    }"""
                                    ))),
                    @ApiResponse(responseCode = "400", description = "Bad Request",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Bad Request Response",
                                            summary = "Example of Bad Request",
                                            value = """
                                                    {
                                                        "requiredType": "Long",
                                                        "parameter": "id",
                                                        "message": "Invalid parameter: NotANumber",
                                                        "status": 400,
                                                        "timestamp": "2024-08-16 09:48:01"
                                                    }
                                                    """
                                    ))),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized - Invalid or missing authentication token",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = @ExampleObject(
                                            name = "Unauthorized Example",
                                            summary = "Example of an unauthorized request due to missing or invalid token",
                                            value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                                    )
                            )
                    )
            }
    )
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('READ_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable Long id){
        UserInfoDTO userInfoDTO = userService.getSingleUser(id);
        return ResponseEntity.ok(userInfoDTO);
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user in the system and returns the details of the newly created user.",
            security = @SecurityRequirement(name = "Security Token"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserRegistrationDTO.class),
                            examples = @ExampleObject(
                                    name = "User Registration Example",
                                    summary = "Example of user registration",
                                    value = """
                                            {
                                                "name": "Jane Doe",
                                                "email": "jane.doe@example.com",
                                                "password": "SecurePass!123",
                                                "role": "USER"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful User Response",
                                    summary = "Example of a successful user creation",
                                    value = """
                                            {
                                                "id": 2,
                                                "name": "Jane Doe",
                                                "email": "jane.doe@example.com",
                                                "registrationDate": "2024-08-16T10:45:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input, object invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Input Response",
                                    summary = "Example of an invalid input error",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Invalid email format",
                                                "timestamp": "2024-08-16T10:50:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @Transactional
    @PostMapping
    public ResponseEntity<UserInfoDTO> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                                       UriComponentsBuilder uriComponentsBuilder){

        // save user entity
        UserInfoDTO userInfoDTO = userService.registerNewUser(userRegistrationDTO);

        // Build the URI for the newly created resource
        URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userInfoDTO.id()).toUri();

        // Return the response with the Location header and the created resource's data
        return ResponseEntity.created(url).body(userInfoDTO);
    }

    @Operation(
            summary = "Update a user's information",
            description = "Updates the information of an existing user identified by their ID.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to update", required = true)
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User update data",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserUpdateDTO.class),
                            examples = @ExampleObject(
                                    name = "User Update Example",
                                    summary = "Example of updating user information",
                                    value = """
                                            {
                                                "name": "John Smith",
                                                "email": "john.smith@example.com",
                                                "password": "NewSecurePass!123"
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful User Update Response",
                                    summary = "Example of a successful user update",
                                    value = """
                                            {
                                                "id": 2,
                                                "name": "John Smith",
                                                "email": "john.smith@example.com",
                                                "registrationDate": "2024-08-16T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input, object invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Input Response",
                                    summary = "Example of an invalid input error",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Invalid email format",
                                                "timestamp": "2024-08-16T11:05:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Not Found Response",
                                    summary = "Example of user not found error",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "User not found with id: 15",
                                                "timestamp": "2024-08-16T11:10:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @Transactional
    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO){
        UserInfoDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(
            summary = "Delete a user",
            description = "Deletes an existing user by their ID.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "id", description = "ID of the user to delete", required = true)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "User deleted successfully",
                    content = @Content()
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "User Not Found Response",
                                    summary = "Example of user not found error",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "User not found with id: 15",
                                                "timestamp": "2024-08-16T11:10:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @Transactional
    @PreAuthorize("hasAuthority('DELETE_USER')")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
