package com.alura.forum.mapper;

import com.alura.forum.model.dto.course.CourseInfoDTO;
import com.alura.forum.model.dto.course.CourseRegistrationDTO;
import com.alura.forum.model.dto.course.CourseUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.model.enums.CourseCategory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourseMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usersEnrolled", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "courseCategory", source = "category", qualifiedByName = "mapCategory")
    Course registerNewCourse(CourseRegistrationDTO registrationDTO);

    @Mapping(target = "category", source = "courseCategory")
    @Mapping(target = "usersEnrolled", source = "usersEnrolled", qualifiedByName = "mapListOfUsers")
    CourseInfoDTO courseToCourseDTO(Course savedCourse);

    List<CourseInfoDTO> coursesToCourseDTO(List<Course> courses);

    @Mapping(target = "usersEnrolled", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "courseCategory", source = "category", qualifiedByName = "mapCategory",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Course updateCourseFromDTO(CourseUpdateDTO courseUpdateDTO, @MappingTarget Course courseUpdated);


    @Named("mapCategory")
    default CourseCategory mapStringCategoryToEnumCategory(String stringCategory) {
        return CourseCategory.valueOf(stringCategory.toUpperCase());
    }

    @Named("mapListOfUsers")
    default List<String> mapUsersToNames(List<UserEntity> listOfUsers){
        return listOfUsers.stream()
                .map(UserEntity::getName)
                .toList();
    }



}
