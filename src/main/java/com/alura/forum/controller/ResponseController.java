package com.alura.forum.controller;

import com.alura.forum.model.dto.response.ResponseInfoDTO;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.model.dto.response.ResponseUpdateDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.service.response.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Responses", description = "Operations about responses")
@RestController
@RequestMapping("/responses")

public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService){
        this.responseService = responseService;
    }


    @Transactional(readOnly = true)
    @Operation(
            summary = "Get responses",
            description = "Retrieve a paginated list of forum responses with optional filtering criteria.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the list of responses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class),
                            examples = @ExampleObject(
                                    name = "Successful Response List",
                                    summary = "Example of a successful response list",
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "message": "This is a response message",
                                                        "topic": "Topic Title",
                                                        "creationDate": "2024-08-16T11:45:00Z",
                                                        "responder": "User Name",
                                                        "solution": "true"
                                                    },
                                                    {
                                                        "id": 2,
                                                        "message": "Another response message",
                                                        "topic": "Another Topic Title",
                                                        "creationDate": "2024-08-16T11:50:00Z",
                                                        "responder": "Another User",
                                                        "solution": "false"
                                                    }
                                                ],
                                                "pageable": {
                                                    "sort": {
                                                        "sorted": true,
                                                        "unsorted": false,
                                                        "empty": false
                                                    },
                                                    "pageNumber": 0,
                                                    "pageSize": 10,
                                                    "offset": 0,
                                                    "paged": true,
                                                    "unpaged": false
                                                },
                                                "totalPages": 1,
                                                "totalElements": 2,
                                                "last": true,
                                                "size": 10,
                                                "number": 0,
                                                "sort": {
                                                    "sorted": true,
                                                    "unsorted": false,
                                                    "empty": false
                                                },
                                                "first": true,
                                                "numberOfElements": 2,
                                                "empty": false
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request - Invalid query parameters",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Bad Request Example",
                                    summary = "Example of a bad request due to invalid parameters",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Invalid 'criteria' parameter value",
                                                "timestamp": "2024-08-16T11:50:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping
    public ResponseEntity<Page<ResponseInfoDTO>> getResponses(@RequestParam(required = false) String criteria,
                                                              @RequestParam(required = false) String value,
                                                              @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ResponseInfoDTO> responsesInDatabase = responseService.getResponses(pageable, criteria, value);
        return ResponseEntity.ok(responsesInDatabase);
    }

    @Transactional(readOnly = true)
    @Operation(
            summary = "Get a single response",
            description = "Retrieve detailed information about a specific forum response by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved the response details",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful Response Retrieval",
                                    summary = "Example of successfully retrieving a single response",
                                    value = """
                                            {
                                                "id": 1,
                                                "message": "This is a response message",
                                                "topic": "Topic Title",
                                                "creationDate": "2024-08-16T11:45:00Z",
                                                "responder": "User Name",
                                                "solution": "true"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Response not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Response Not Found",
                                    summary = "Example of a response not being found by ID",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Response with ID 1 not found",
                                                "timestamp": "2024-08-16T11:50:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ResponseInfoDTO> getResponse(@PathVariable Long id){
        ResponseInfoDTO responseInfoDTO = responseService.getSingleResponse(id);
        return ResponseEntity.ok(responseInfoDTO);
    }

    @Transactional
    @Operation(
            summary = "Add a new response",
            description = "Registers a new response to a specific topic and returns the details of the newly created response.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to register a new topic",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseRegistrationDTO.class),
                    examples = @ExampleObject(
                            name = "Response Registration Request",
                            summary = "Example of a request to register a new response",
                            value = """
                                    {
                                        "message": "Follow these instructions to learn Spring Boot: ?",
                                        "topicId": "1",
                                        "responderId": "3"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Response created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Successful Response Creation",
                                    summary = "Example of a successful response creation",
                                    value = """
                                            {
                                                "id": 1,
                                                "message": "This is a response message",
                                                "topic": "Topic Title",
                                                "creationDate": "2024-08-16T12:00:00Z",
                                                "responder": "User Name",
                                                "solution": "false"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input, object invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Input Example",
                                    summary = "Example of invalid input causing a bad request",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Validation failed for argument [0] in public ResponseInfoDTO",
                                                "timestamp": "2024-08-16T12:05:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T12:15:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
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
    @Operation(
            summary = "Update an existing response",
            description = "Updates the details of an existing response in the forum by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to update an existing response",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ResponseUpdateDTO.class),
                    examples = @ExampleObject(
                            name = "Response Update Request",
                            summary = "Example of a request to update an existing response",
                            value = """
                                    {
                                        "message": "Updated response message",
                                        "solution": "true"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Response updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Response Update Response",
                                    summary = "Example of a successful response after updating a response",
                                    value = """
                                            {
                                                "id": 1,
                                                "message": "Updated response message",
                                                "topic": "How to implement Swagger in Spring Boot",
                                                "creationDate": "2024-08-16T12:30:00Z",
                                                "responder": "John Doe",
                                                "solution": "true"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input, object invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Invalid Input Response",
                                    summary = "Example of a response when input is invalid",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Validation failed for object='responseUpdateDTO'. Error count: 1",
                                                "timestamp": "2024-08-16T12:35:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Response not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Response Not Found Response",
                                    summary = "Example of a response when the response to update is not found",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Response with ID '1' not found",
                                                "timestamp": "2024-08-16T12:40:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseInfoDTO> updateResponse(@PathVariable Long id, @RequestBody ResponseUpdateDTO updateDTO){
        ResponseInfoDTO responseInfoDTO = responseService.updateResponse(id, updateDTO);
        return ResponseEntity.ok(responseInfoDTO);
    }

    @Transactional
    @Operation(
            summary = "Delete a response",
            description = "Deletes a response by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Response deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Response not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Response Not Found",
                                    summary = "Example of a response when the response to delete is not found",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Response with ID '1' not found",
                                                "timestamp": "2024-08-16T12:45:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing authentication token",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Unauthorized Example",
                                    summary = "Example of an unauthorized request due to missing or invalid token",
                                    value = """
                                            {
                                                "status": 401,
                                                "message": "Unauthorized access - Token is missing or invalid",
                                                "timestamp": "2024-08-16T11:55:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id){
        responseService.deleteResponse(id);
        return ResponseEntity.noContent().build();
    }

}
