package com.alura.forum.mapper;

import com.alura.forum.model.dto.user.UserUpdateDTO;
import com.alura.forum.model.entity.User;
import com.alura.forum.model.dto.user.UserInfoDTO;
import com.alura.forum.model.dto.user.UserRegistrationDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(source = "registrationDate", target = "registrationDate", dateFormat = "yyyy-MM-dd")
    UserInfoDTO userToUserInfoDTO(User userEntity);

    List<UserInfoDTO> usersToUserInfoListDTO(List<User> userList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDate.now())")
    User registerUserFromDTO(UserRegistrationDTO userRegistrationDTO);

    @Mapping(target = "registrationDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(source = "name", target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "email", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "password", target = "password", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User userUpdated);
}
