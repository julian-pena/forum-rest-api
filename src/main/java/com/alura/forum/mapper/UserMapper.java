package com.alura.forum.mapper;

import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.PermissionEntity;
import com.alura.forum.model.entity.RoleEntity;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import com.alura.forum.model.enums.RoleEnum;
import com.alura.forum.repository.RoleRepository;
import com.alura.forum.repository.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Mapping(source = "registrationDate", target = "registrationDate", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "courses", target = "courses", qualifiedByName = "mapCourses")
    UserInfoDTO userToUserInfoDTO(UserEntity userEntity);

    List<UserInfoDTO> usersToUserInfoListDTO(List<UserEntity> userEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", source = "password", qualifiedByName = "mapPassword")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "credentialsNonExpired", constant = "true")
    @Mapping(target = "accountNonLocked", constant = "true")
    @Mapping(target = "accountNonExpired", constant = "true")
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    UserEntity registerUserFromDTO(UserRegistrationDTO userRegistrationDTO);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "credentialsNonExpired", ignore = true)
    @Mapping(target = "accountNonLocked", ignore = true)
    @Mapping(target = "accountNonExpired", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "course", target = "courses", qualifiedByName = "addNewCourse")
    @Mapping(source = "userUpdateDTO.name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userUpdateDTO.email", target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userUpdateDTO.password", target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, qualifiedByName = "mapPassword")
    UserEntity updateUserFromUserDTO(UserUpdateDTO userUpdateDTO, Course course, @MappingTarget UserEntity userEntityUpdated);


    @Named("mapCourses")
    default Set<String> mapCoursesToCoursesNames(Set<Course> courses){
        return courses.stream()
                .map(Course::getName)
                .collect(Collectors.toSet());
    }

    @Named("mapPassword")
    default String mapPassword(String password){
        return passwordEncoder.encode(password);
    }

    @Named("addNewCourse")
    default void addCourseToSetOfCoursesFromUser(Course course, @MappingTarget Set<Course> courses){
        courses.add(course);
    }


}
