package com.alura.forum.controller;

import com.alura.forum.model.dto.topic.TopicInfoDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.model.dto.topic.TopicUpdateDTO;
import com.alura.forum.service.topic.TopicService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }


    @GetMapping
    public ResponseEntity<Page<TopicInfoDTO>> getTopics(@RequestParam(required = false) String criteria,
                                                        @RequestParam(required = false) String value,
                                                        Pageable pageable){
        Page<TopicInfoDTO> topicsInDatabase = topicService.getAllTopics(pageable, criteria, value);
        return ResponseEntity.ok(topicsInDatabase);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long id){
        TopicInfoDTO topicInDatabase =  topicService.getSingleTopic(id);
        return ResponseEntity.ok(topicInDatabase);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<TopicInfoDTO> addTopic(@RequestBody @Valid TopicRegistrationDTO registrationDTO, UriComponentsBuilder uriComponentsBuilder){
        // Save user entity
        TopicInfoDTO topicInfoDTO = topicService.registerNewTopic(registrationDTO);
        // Build the URI for the newly created resource
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        // Return the response with the Location header and the created resource's data
        return ResponseEntity.created(url).body(topicInfoDTO);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicInfoDTO> updateTopic(@Valid @RequestBody TopicUpdateDTO updateDTO, @PathVariable Long id){
        var topicInfoDTO = topicService.updateTopic(id, updateDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
