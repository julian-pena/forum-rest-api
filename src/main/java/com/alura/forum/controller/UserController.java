package com.alura.forum.controller;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserDetailsDTO;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.service.user.UserService;
import jakarta.transaction.Transactional;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserInfoDTO>> getUsers(Pageable pageable){
        Page<UserInfoDTO> usersInDatabase = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersInDatabase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDTO> getUser(@PathVariable Long id){
        UserDetailsDTO usersInDatabase = userService.getUserDetails(id);
        return ResponseEntity.ok(usersInDatabase);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDetailsDTO> registerUser(@RequestBody @Valid UserRegistrationDTO userRegistrationDTO,
                                                       UriComponentsBuilder uriComponentsBuilder){

        // save user entity
        UserDetailsDTO userDetailsDTO = userService.registerNewUser(userRegistrationDTO);

        // Build the URI for the newly created resource
        URI url = uriComponentsBuilder.path("/users/{id}").buildAndExpand(userDetailsDTO.id()).toUri();

        // Return the response with the Location header and the created resource's data
        return ResponseEntity.created(url).body(userDetailsDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO){
        UserDetailsDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
