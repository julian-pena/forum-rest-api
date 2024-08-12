package com.alura.forum.mapper;

import com.alura.forum.model.dto.response.ResponseInfoDTO;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.model.dto.response.ResponseUpdateDTO;
import com.alura.forum.model.entity.Response;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.model.entity.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResponseMapper {

    @Mapping(target = "topic", source = "topic.title")
    @Mapping(target = "responder", source = "responder.name")
    @Mapping(target = "creationDate", source = "creationDate", dateFormat = "yyyy-MM-dd HH:mm")
    @Mapping(target = "solution", source = "solution", qualifiedByName = "mapSolution")
    ResponseInfoDTO responseToResponseDTO(Response response);

    List<ResponseInfoDTO> responsesToResponsesDTOList(List<Response> responses);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate" , expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "solution", constant = "false")
    @Mapping(target = "message", source = "registrationDTO.message")
    @Mapping(target = "topic", source = "topic")
    @Mapping(target = "responder", source = "responder")
    Response registerResponseFromDTO(ResponseRegistrationDTO registrationDTO, Topic topic, UserEntity responder);

    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "responder", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    Response updateResponseFromDTO(ResponseUpdateDTO responseUpdateDTO, @MappingTarget Response responseUpdated);


    @Named("mapSolution")
    default String mapSolution(Boolean solution){
        if(solution){
            return "Yes";
        }
        return "No";
    }

}
