package com.alura.forum.service.user;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.mapper.UserMapper;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class  UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> getAllUsers(Pageable pageable) {
        Page<UserEntity> usersPage = userRepository.findAll(pageable);
        List<UserInfoDTO> userInfoDTOS = userMapper.usersToUserInfoListDTO(usersPage.getContent());
        return new PageImpl<>(userInfoDTOS, pageable, usersPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public UserInfoDTO getSingleUser(Long id) {
        UserEntity userEntity = findUserById(id);
        return userMapper.userToUserInfoDTO(userEntity);
    }

    @Transactional
    public UserInfoDTO registerNewUser(UserRegistrationDTO userRegistrationDTO) {
        UserEntity userEntityToRegister = userMapper.registerUserFromDTO(userRegistrationDTO);
        UserEntity userEntityRegistered =  userRepository.save(userEntityToRegister);
        return userMapper.userToUserInfoDTO(userEntityRegistered);
    }

    @Transactional
    public UserInfoDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws ResourceNotFoundException {
        // Find topic by ID. An exception will throw if ID is not found.
        UserEntity userEntityUpdated = findUserById(id);
        // Map new properties to entity from DTO and persist
        userEntityUpdated = userMapper.updateUserFromUserDTO(userUpdateDTO, userEntityUpdated);
        userRepository.save(userEntityUpdated);
        // Return DTO with updated properties
        return userMapper.userToUserInfoDTO(userEntityUpdated);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    private UserEntity findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

}

