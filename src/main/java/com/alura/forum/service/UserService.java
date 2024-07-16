package com.alura.forum.service;

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
import java.time.LocalDate;
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

    public UserDetailsDTO getUserDetails(Long id) throws ResourceNotFoundException {
        User user = findUserById(id);
        return userMapper.userToUserDetailInfoDTO(user);
    }

    public UserDetailsDTO registerNewUser(UserRegistrationDTO userRegistrationDTO) {
        User userToRegister = userMapper.registerUserFromDTO(userRegistrationDTO);
        userToRegister.setRegistrationDate(LocalDate.now());
        User userRegistered =  userRepository.save(userToRegister);
        return userMapper.userToUserDetailInfoDTO(userRegistered);
    }


    public UserDetailsDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws ResourceNotFoundException {
        User userToUpdate = findUserById(id);
        User userUpdated = userMapper.updateUserFromUserDTO(userUpdateDTO, userToUpdate);
        System.out.println(userMapper.userToUserDetailInfoDTO(userUpdated));
        userRepository.save(userToUpdate);
        return userMapper.userToUserDetailInfoDTO(userUpdated);
    }

    private User findUserById(Long id) throws ResourceNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Error finding user id: " + id);
        }
        return optionalUser.get();
    }
}

