package com.alura.forum.service.user;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserDetailsDTO;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.model.entity.User;
import com.alura.forum.mapper.UserMapper;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

@Service
public class  UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public Page<UserInfoDTO> getAllUsers(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        List<UserInfoDTO> userInfoDTOS = userMapper.usersToUserInfoListDTO(usersPage.getContent());
        return new PageImpl<>(userInfoDTOS, pageable, usersPage.getTotalElements());
    }

    public UserDetailsDTO getUserDetails(Long id) {
        User user = findUserById(id);
        return userMapper.userToUserDetailInfoDTO(user);
    }

    public UserDetailsDTO registerNewUser(UserRegistrationDTO userRegistrationDTO) {
        User userToRegister = userMapper.registerUserFromDTO(userRegistrationDTO);
        User userRegistered =  userRepository.save(userToRegister);
        return userMapper.userToUserDetailInfoDTO(userRegistered);
    }


    public UserDetailsDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws ResourceNotFoundException {
        // Find topic by ID. An exception will throw if ID is not found.
        User userUpdated = findUserById(id);
        // Map new properties to entity from DTO and persist
        userUpdated = userMapper.updateUserFromUserDTO(userUpdateDTO, userUpdated);
        userRepository.save(userUpdated);
        // Return DTO with updated properties
        return userMapper.userToUserDetailInfoDTO(userUpdated);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private User findUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return optionalUser.get();
    }

}

