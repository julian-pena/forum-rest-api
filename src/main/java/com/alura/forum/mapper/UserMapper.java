package com.alura.forum.mapper;

import com.alura.forum.model.dto.user.UserUpdateDTO;
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
    @Mapping(target = "courses", ignore = true)
    @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, qualifiedByName = "mapPassword")
    UserEntity updateUserFromUserDTO(UserUpdateDTO userUpdateDTO, @MappingTarget UserEntity userEntityUpdated);


    /*
    @Named("mapNewRole")
    default Set<RoleEntity> mapNewRole(String stringRole) {

        RoleEnum roleEnum = RoleEnum.valueOf(stringRole.toUpperCase());
        RoleEntity roleEntity = new RoleEntity(roleEnum);

        roleEntity.setPermissionsList(roleEnum.getPermissionEnums().stream()
                 .map(PermissionEntity::new)
                 .collect(Collectors.toSet()));

        return Stream.of(roleEntity).collect(Collectors.toSet());
    } */

    @Named("mapPassword")
    default String mapPassword(String password){
        return passwordEncoder.encode(password);
    }

    private Set<RoleEntity> something(String value){
        return null;
    }


}
