package com.alura.forum.service.response;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.mapper.ResponseMapper;
import com.alura.forum.model.dto.response.ResponseInfoDTO;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.model.dto.response.ResponseUpdateDTO;
import com.alura.forum.model.entity.Response;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.model.entity.User;
import com.alura.forum.repository.ResponseRepository;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.repository.UserRepository;
import com.alura.forum.service.response.search.ResponseSearchCriteria;
import com.alura.forum.service.response.search.ResponseSearchCriteriaFactory;
import com.alura.forum.service.response.validations.ResponseValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final ResponseMapper responseMapper;
    private final ResponseSearchCriteriaFactory criteriaFactory;
    private final List<ResponseValidations> validations;

    @Autowired
    public ResponseService(ResponseRepository responseRepository,TopicRepository topicRepository, UserRepository userRepository,
                           ResponseMapper responseMapper, ResponseSearchCriteriaFactory criteriaFactory, List<ResponseValidations> validations){
        this.responseRepository = responseRepository;
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.validations = validations;
        this.criteriaFactory = criteriaFactory;
        this.responseMapper = responseMapper;
    }

    @Transactional(readOnly = true)
    public ResponseInfoDTO getSingleResponse(Long id) {
        Response response = findResponseById(id);
        return responseMapper.responseToResponseDTO(response);
    }

    @Transactional(readOnly = true)
    public Page<ResponseInfoDTO> getResponses(Pageable pageable, String criteria, String value){
        Page<Response> responsePage;
        // Search for criteria and value if provided
        if (criteria != null && value != null) {
            ResponseSearchCriteria searchCriteria = criteriaFactory.getCriteria(criteria);
            responsePage = searchCriteria != null ? searchCriteria.applyCriteria(pageable, value) : responseRepository.findAll(pageable);
        } else {
            responsePage = responseRepository.findAll(pageable);
        }
        // Map to DTO and return page
        List<ResponseInfoDTO> responseInfoDTOS = responseMapper.responsesToResponsesDTOList(responsePage.getContent());
        return new PageImpl<>(responseInfoDTOS, pageable, responsePage.getTotalElements());
    }

    @Transactional
    public ResponseInfoDTO registerNewResponse(ResponseRegistrationDTO registrationDTO) {
        validations.forEach(v -> v.valid(registrationDTO));

        Topic topic = topicRepository.getReferenceById(Long.parseLong(registrationDTO.topicId()));
        User responder = userRepository.getReferenceById(Long.parseLong(registrationDTO.responderId()));

        Response response = responseMapper.registerResponseFromDTO(registrationDTO, topic, responder);

        Response savedResponse = responseRepository.save(response);

        return responseMapper.responseToResponseDTO(savedResponse);
    }

    @Transactional
    public ResponseInfoDTO updateResponse(Long id, ResponseUpdateDTO responseUpdateDTO) {
        Response responseUpdated = findResponseById(id);
        responseUpdated = responseMapper.updateResponseFromDTO(responseUpdateDTO, responseUpdated);
        responseRepository.save(responseUpdated);
        return responseMapper.responseToResponseDTO(responseUpdated);
    }

    @Transactional
    public void deleteResponse(Long id) {
        if(!responseRepository.existsById(id)){
            throw new ResourceNotFoundException("Response not found with id: " + id);
        }
        responseRepository.deleteById(id);
    }


    private Response findResponseById(Long id) {
        return responseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Response not found with id: " + id));
    }

}
