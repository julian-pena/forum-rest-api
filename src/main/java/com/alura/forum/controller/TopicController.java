package com.alura.forum.controller;

import com.alura.forum.model.dto.topic.TopicInfoDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.model.dto.topic.TopicUpdateDTO;
import com.alura.forum.service.topic.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;

@Tag(name = "Topics", description = "Operations about topics")
@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService){
        this.topicService = topicService;
    }


    @Transactional(readOnly = true)
    @Operation(
            summary = "Retrieve all topics",
            description = "Fetches a paginated list of all forum topics. Allows filtering by specific criteria such as title, author, or course name.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "criteria", description = "Filtering criteria (e.g., title, authorName, courseName)"),
                    @Parameter(name = "value", description = "Value for the filtering criteria"),
                    @Parameter(name = "page", description = "Page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of topics per page", example = "10"),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending by creationDate", example = "creationDate,asc")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of topics",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class),
                            examples = @ExampleObject(
                                    name = "Topics Response",
                                    summary = "Example of a successful response",
                                    value = """
                                            {
                                                "content": [
                                                    {
                                                        "id": 1,
                                                        "title": "Java Basics",
                                                        "message": "How do I start learning Java?",
                                                        "creationDate": "2024-08-16T09:30:00Z",
                                                        "forumStatus": "OPEN",
                                                        "authorName": "Alice Johnson",
                                                        "courseName": "Java for Beginners",
                                                        "totalResponses": 5
                                                    },
                                                    {
                                                        "id": 2,
                                                        "title": "Spring Boot",
                                                        "message": "Best practices for Spring Boot?",
                                                        "creationDate": "2024-08-16T10:00:00Z",
                                                        "forumStatus": "CLOSED",
                                                        "authorName": "Bob Smith",
                                                        "courseName": "Spring Boot Essentials",
                                                        "totalResponses": 3
                                                    }
                                                ],
                                                "pageable": {
                                                    "sort": {
                                                        "sorted": true,
                                                        "unsorted": false,
                                                        "empty": false
                                                    },
                                                    "offset": 0,
                                                    "pageNumber": 0,
                                                    "pageSize": 10,
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
                    description = "Invalid request parameters",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Bad Request Response",
                                    summary = "Example of invalid request",
                                    value = """
                                            {
                                                "status": 400,
                                                "message": "Invalid sorting criteria",
                                                "timestamp": "2024-08-16T11:10:00Z"
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
    public ResponseEntity<Page<TopicInfoDTO>> getTopics(@RequestParam(required = false) String criteria,
                                                        @RequestParam(required = false) String value,
                                                        @PageableDefault(direction = Sort.Direction.ASC, size = 20) Pageable pageable){
        Page<TopicInfoDTO> topicsInDatabase = topicService.getAllTopics(pageable, criteria, value);
        return ResponseEntity.ok(topicsInDatabase);
    }


    @Transactional(readOnly = true)
    @Operation(
            summary = "Retrieve a single topic by ID",
            description = "Fetches a specific topic from the forum using its unique ID.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "id", description = "ID of the topic to retrieve", required = true, example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of the topic",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TopicInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Topic Response",
                                    summary = "Example of a successful response",
                                    value = """
                                            {
                                                "id": 1,
                                                "title": "Java Basics",
                                                "message": "How do I start learning Java?",
                                                "creationDate": "2024-08-16T09:30:00Z",
                                                "forumStatus": "OPEN",
                                                "authorName": "Alice Johnson",
                                                "courseName": "Java for Beginners",
                                                "totalResponses": 5
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Topic not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Topic Not Found Response",
                                    summary = "Example of a topic not found response",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Topic not found with id: 15",
                                                "timestamp": "2024-08-16T11:30:00Z"
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
    public ResponseEntity<TopicInfoDTO> getTopic(@PathVariable Long id){
        TopicInfoDTO topicInDatabase =  topicService.getSingleTopic(id);
        return ResponseEntity.ok(topicInDatabase);
    }

    @Transactional
    @Operation(
            summary = "Register a new topic",
            description = "Creates a new topic in the forum and returns the details of the newly created topic.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to register a new topic",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopicRegistrationDTO.class),
                    examples = @ExampleObject(
                            name = "Topic Registration Request",
                            summary = "Example of a request to register a new topic",
                            value = """
                                    {
                                        "title": "Understanding Spring Boot",
                                        "message": "How does Spring Boot work?",
                                        "authorId": "1",
                                        "courseId": "3"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Topic created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TopicInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Topic Response",
                                    summary = "Example of a successful response",
                                    value = """
                                            {
                                                "id": 1,
                                                "title": "Understanding Spring Boot",
                                                "message": "How does Spring Boot work?",
                                                "creationDate": "2024-08-16T09:30:00Z",
                                                "forumStatus": "OPEN",
                                                "authorName": "Alice Johnson",
                                                "courseName": "Java for Beginners",
                                                "totalResponses": 0
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
                                                "timeStamp": "2024-08-19 20:51:07",
                                                "title": "Title  already exists: The goat. Use a different title",
                                                "status": 400
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course or Author don't exists",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Topic Conflict Response",
                                    summary = "Example of a response when a author or course don't exists",
                                    value = """
                                            {
                                                "timeStamp": "2024-08-19 20:51:40",
                                                "message": "User not found with id: 20",
                                                "status": 404
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
    @PostMapping
    public ResponseEntity<TopicInfoDTO> addTopic(@RequestBody @Valid TopicRegistrationDTO registrationDTO, UriComponentsBuilder uriComponentsBuilder){
        // Save user entity
        TopicInfoDTO topicInfoDTO = topicService.registerNewTopic(registrationDTO);
        // Build the URI for the newly created resource
        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(topicInfoDTO.id()).toUri();
        // Return the response with the Location header and the created resource's data
        return ResponseEntity.created(url).body(topicInfoDTO);
    }

    @Transactional
    @Operation(
            summary = "Update an existing topic",
            description = "Updates the details of an existing topic in the forum by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to update an existing topic",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TopicUpdateDTO.class),
                    examples = @ExampleObject(
                            name = "Topic Update Request",
                            summary = "Example of a request to update an existing topic",
                            value = """
                                    {
                                        "title": "Updated Topic Title",
                                        "message": "Updated message content for the topic.",
                                        "forumStatus": "CLOSED"
                                    }
                                    """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Topic updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TopicInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Topic Update Response",
                                    summary = "Example of a successful response after updating a topic",
                                    value = """
                                            {
                                                "id": 1,
                                                "title": "Updated Topic Title",
                                                "message": "Updated message content for the topic.",
                                                "creationDate": "2024-08-16T09:30:00Z",
                                                "forumStatus": "CLOSED",
                                                "authorName": "Alice Johnson",
                                                "courseName": "Java for Beginners",
                                                "totalResponses": 5
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
                                                "message": "Validation failed for object='topicUpdateDTO'. Error count: 1",
                                                "timestamp": "2024-08-16T11:30:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Topic not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Topic Not Found Response",
                                    summary = "Example of a response when the topic to update is not found",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Topic with ID '1' not found",
                                                "timestamp": "2024-08-16T11:35:00Z"
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
    public ResponseEntity<TopicInfoDTO> updateTopic(@Valid @RequestBody TopicUpdateDTO updateDTO, @PathVariable Long id){
        var topicInfoDTO = topicService.updateTopic(id, updateDTO);
        return ResponseEntity.ok(topicInfoDTO);
    }

    @Transactional
    @Operation(
            summary = "Delete a topic",
            description = "Deletes an existing topic from the forum by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Topic deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Topic not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Topic Not Found Response",
                                    summary = "Example of a response when the topic to delete is not found",
                                    value = """
                                            {
                                                "status": 404,
                                                "message": "Topic with ID '1' not found",
                                                "timestamp": "2024-08-16T11:40:00Z"
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
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id){
        topicService.deleteTopic(id);
        return ResponseEntity.noContent().build();
    }
}
