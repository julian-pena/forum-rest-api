package com.alura.forum.controller;

import com.alura.forum.model.dto.response.ResponseInfoDTO;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.model.dto.response.ResponseUpdateDTO;
import com.alura.forum.service.response.ResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.transaction.annotation.Transactional;
import java.net.URI;

@RestController
@RequestMapping("/responses")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService){
        this.responseService = responseService;
    }


    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<ResponseInfoDTO>> getResponses(@RequestParam(required = false) String criteria,
                                                              @RequestParam(required = false) String value,
                                                              @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ResponseInfoDTO> responsesInDatabase = responseService.getResponses(pageable, criteria, value);
        return ResponseEntity.ok(responsesInDatabase);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<ResponseInfoDTO> getResponse(@PathVariable Long id){
        ResponseInfoDTO responseInfoDTO = responseService.getSingleResponse(id);
        return ResponseEntity.ok(responseInfoDTO);
    }

    @Transactional
    @PostMapping
    public ResponseEntity<ResponseInfoDTO> addResponse(@RequestBody @Valid ResponseRegistrationDTO registrationDTO, UriComponentsBuilder uriComponentsBuilder){
        // Save user entity
        ResponseInfoDTO responseInfoDTO = responseService.registerNewResponse(registrationDTO);
        // Build the URI for the newly created resource
        URI url = uriComponentsBuilder.path("/responses/{id}").buildAndExpand(responseInfoDTO.id()).toUri();
        // Return the response with the Location header and the created resource's data
        return ResponseEntity.created(url).body(responseInfoDTO);
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<ResponseInfoDTO> updateResponse(@PathVariable Long id, @RequestBody ResponseUpdateDTO updateDTO){
        ResponseInfoDTO responseInfoDTO = responseService.updateResponse(id, updateDTO);
        return ResponseEntity.ok(responseInfoDTO);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id){
        responseService.deleteResponse(id);
        return ResponseEntity.noContent().build();
    }

}
