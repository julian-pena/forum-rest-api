package com.alura.forum.controller;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.service.user.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<UserInfoDTO>> getUsers(@PageableDefault(sort = "registrationDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserInfoDTO> usersInDatabase = userService.getAllUsers(pageable);
        return ResponseEntity.ok(usersInDatabase);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> getUser(@PathVariable Long id){
        UserInfoDTO userInfoDTO = userService.getSingleUser(id);
        return ResponseEntity.ok(userInfoDTO);
    }

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

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<UserInfoDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO){
        UserInfoDTO updatedUser = userService.updateUser(id, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
