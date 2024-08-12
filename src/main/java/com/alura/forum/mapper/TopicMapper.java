package com.alura.forum.mapper;

import com.alura.forum.model.dto.topic.TopicInfoDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.model.dto.topic.TopicUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.Response;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.model.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TopicMapper {

    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "courseName", source = "course.name")
    @Mapping(target = "creationDate", source = "creationDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "totalResponses", source = "responses", qualifiedByName = "mapResponses")
    TopicInfoDTO topicToTopicInfoDTO(Topic topic);

    List<TopicInfoDTO> topicsToTopicInfoDTOList(List<Topic> topicList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "responses", ignore = true)
    @Mapping(target = "forumStatus" , expression = "java(ForumStatus.OPEN)")
    @Mapping(target = "creationDate" , expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "author", source = "userEntity")
    @Mapping(target = "course", source = "course")
    Topic registerTopicFromDTO(TopicRegistrationDTO registrationDTO, UserEntity userEntity, Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "responses", ignore = true)
    @Mapping(source = "title", target = "title", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "message", target = "message", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "forumStatus", target = "forumStatus", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Topic updateTopicFromDTO(TopicUpdateDTO topicUpdateDTO,  @MappingTarget Topic topicToUpdate);


    @Named("mapResponses")
    default Long mapResponsesToLong(List<Response> responses){
        return (long) responses.size();
    }

}

