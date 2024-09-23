package com.alura.forum.controller;

import com.alura.forum.model.dto.course.CourseInfoDTO;
import com.alura.forum.model.dto.course.CourseRegistrationDTO;
import com.alura.forum.model.dto.course.CourseUpdateDTO;
import com.alura.forum.service.course.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "Courses", description = "Operations about courses")
@RestController
@RequestMapping("/courses")
public class CourseController {


    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @Operation(
            summary = "Retrieve all courses",
            description = "Fetches a paginated list of all available courses. Allows filtering by specific criteria such as course category or instructor.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "criteria", description = "Filtering criteria (e.g., category, instructorName)"),
                    @Parameter(name = "value", description = "Value for the filtering criteria"),
                    @Parameter(name = "page", description = "Page number (starting from 0)", example = "0"),
                    @Parameter(name = "size", description = "Number of courses per page", example = "10"),
                    @Parameter(name = "sort", description = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending by usersEnrolled", example = "usersEnrolled,asc")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of courses",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Page.class),
                            examples = @ExampleObject(
                                    name = "Courses Response",
                                    summary = "Example of a successful response",
                                    value = """
                    {
                        "content": [
                            {
                                "id": 1,
                                "title": "Java Programming",
                                "description": "Comprehensive Java course",
                                "category": "BACK_END_DEVELOPMENT",
                                "instructorName": "John Doe",
                                "usersEnrolled": 200
                            },
                            {
                                "id": 2,
                                "title": "Cloud Computing Essentials",
                                "description": "Introduction to Cloud Computing",
                                "category": "CLOUD_COMPUTING",
                                "instructorName": "Jane Smith",
                                "usersEnrolled": 150
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
                        "message": "Invalid filtering criteria",
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
    @Transactional(readOnly = true)
    @GetMapping
    public ResponseEntity<Page<CourseInfoDTO>> getAllCourses(
            @RequestParam(required = false) String criteria,
            @RequestParam(required = false) String value,
            @PageableDefault(sort = "usersEnrolled", direction = Sort.Direction.ASC, size = 20) Pageable pageable){

        Page<CourseInfoDTO> coursesPage = courseService.getCourses(pageable, criteria, value);
        return ResponseEntity.ok(coursesPage);
    }

    @Operation(
            summary = "Retrieve a single course by ID",
            description = "Fetches a specific course from the catalog using its unique ID.",
            security = @SecurityRequirement(name = "Security Token"),
            parameters = {
                    @Parameter(name = "id", description = "ID of the course to retrieve", required = true, example = "1")
            }
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of the course",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CourseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Course Response",
                                    summary = "Example of a successful response",
                                    value = """
                    {
                        "id": 1,
                        "title": "Java Programming",
                        "description": "Comprehensive Java course",
                        "category": "BACK_END_DEVELOPMENT",
                        "instructorName": "John Doe",
                        "usersEnrolled": 200
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Course Not Found Response",
                                    summary = "Example of a course not found response",
                                    value = """
                    {
                        "status": 404,
                        "message": "Course not found with id: 15",
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
    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<CourseInfoDTO> getCourse(@PathVariable Long id){
        CourseInfoDTO courseInfoDTO = courseService.getSingleCourse(id);
        return ResponseEntity.ok(courseInfoDTO);
    }

    @Operation(
            summary = "Register a new course",
            description = "Creates a new course in the catalog and returns the details of the newly created course.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to register a new course",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseRegistrationDTO.class),
                    examples = @ExampleObject(
                            name = "Course Registration Request",
                            summary = "Example of a request to register a new course",
                            value = """
                {
                    "title": "Advanced Java Programming",
                    "description": "An in-depth course on advanced Java concepts",
                    "category": "BACK_END_DEVELOPMENT",
                    "instructorId": "1"
                }
                """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Course created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CourseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Course Response",
                                    summary = "Example of a successful response",
                                    value = """
                    {
                        "id": 1,
                        "title": "Advanced Java Programming",
                        "description": "An in-depth course on advanced Java concepts",
                        "category": "BACK_END_DEVELOPMENT",
                        "instructorName": "John Doe",
                        "usersEnrolled": 0
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
                        "timeStamp": "2024-08-19T20:51:07Z",
                        "message": "Title already exists: Advanced Java Programming. Use a different title",
                        "status": 400
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Instructor not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Instructor Not Found Response",
                                    summary = "Example of a response when the instructor does not exist",
                                    value = """
                    {
                        "timeStamp": "2024-08-19T20:51:40Z",
                        "message": "Instructor not found with id: 20",
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
    @Transactional
    @PostMapping
    public ResponseEntity<CourseInfoDTO> postNewCourse(@RequestBody @Valid CourseRegistrationDTO registrationDTO, UriComponentsBuilder uriComponentsBuilder){
        CourseInfoDTO courseInfoDTO = courseService.addNewCourse(registrationDTO);

        URI url = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(courseInfoDTO.id()).toUri();

        return ResponseEntity.created(url).body(courseInfoDTO);
    }

    @Operation(
            summary = "Update an existing course",
            description = "Updates the details of an existing course by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data required to update an existing course",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CourseUpdateDTO.class),
                    examples = @ExampleObject(
                            name = "Course Update Request",
                            summary = "Example of a request to update an existing course",
                            value = """
                {
                    "title": "Updated Course Title",
                    "description": "Updated course description.",
                    "category": "FULL_STACK_DEVELOPMENT"
                }
                """
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Course updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CourseInfoDTO.class),
                            examples = @ExampleObject(
                                    name = "Course Update Response",
                                    summary = "Example of a successful response after updating a course",
                                    value = """
                    {
                        "id": 1,
                        "title": "Updated Course Title",
                        "description": "Updated course description.",
                        "category": "FULL_STACK_DEVELOPMENT",
                        "instructorName": "John Doe",
                        "usersEnrolled": 25
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
                        "message": "Validation failed for object='courseUpdateDTO'. Error count: 1",
                        "timestamp": "2024-08-19T11:30:00Z"
                    }
                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Course Not Found Response",
                                    summary = "Example of a response when the course to update is not found",
                                    value = """
                    {
                        "status": 404,
                        "message": "Course with ID '1' not found",
                        "timestamp": "2024-08-19T11:35:00Z"
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
                        "timestamp": "2024-08-19T11:55:00Z"
                    }
                    """
                            )
                    )
            )
    })
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<CourseInfoDTO> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateDTO courseUpdateDTO){
        CourseInfoDTO updatedDTO = courseService.updateCourseFromDTO(id, courseUpdateDTO);
        return ResponseEntity.ok(updatedDTO);
    }

    @Operation(
            summary = "Delete a course",
            description = "Deletes an existing course by its ID.",
            security = @SecurityRequirement(name = "Security Token")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Course deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Course not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Course Not Found Response",
                                    summary = "Example of a response when the course to delete is not found",
                                    value = """
                    {
                        "status": 404,
                        "message": "Course with ID '1' not found",
                        "timestamp": "2024-08-19T11:40:00Z"
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
                        "timestamp": "2024-08-19T11:55:00Z"
                    }
                    """
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
