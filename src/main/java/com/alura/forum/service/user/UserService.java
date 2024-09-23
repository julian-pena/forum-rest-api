package com.alura.forum.service.user;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.RoleEntity;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.mapper.UserMapper;
import com.alura.forum.model.enums.RoleEnum;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.repository.RoleRepository;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class  UserService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, CourseRepository courseRepository,
                       RoleRepository roleRepository, UserMapper userMapper){
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.roleRepository = roleRepository;
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
        RoleEntity roleEntity = roleRepository.findByRole(RoleEnum.valueOf(userRegistrationDTO.role().toUpperCase()))
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        UserEntity userEntityToRegister = userMapper.registerUserFromDTO(userRegistrationDTO);
        userEntityToRegister.setRoles(Collections.singleton(roleEntity));
        UserEntity userEntityRegistered =  userRepository.save(userEntityToRegister);
        return userMapper.userToUserInfoDTO(userEntityRegistered);
    }

    @Transactional
    public UserInfoDTO updateUser(Long id, UserUpdateDTO userUpdateDTO) throws ResourceNotFoundException {
        // Find topic by ID. An exception will throw if ID is not found.
        UserEntity userEntityUpdated = findUserById(id);
        // Find Course if present in updateDTO. An exception will be thrown if courseID is not present.
        Course newCourse = null;
        if(userUpdateDTO.newCourseId() != null) {
             newCourse = findCourseById(Long.valueOf(userUpdateDTO.newCourseId())); // newCourseId will always be a long value if it is not null
        }
        // Map new properties to entity from DTO and persist
        userEntityUpdated = userMapper.updateUserFromUserDTO(userUpdateDTO, newCourse, userEntityUpdated);
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

    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

}

