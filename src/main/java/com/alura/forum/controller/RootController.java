package com.alura.forum.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Root", description = "Operations to retrieve API resources")
@RestController
public class RootController {

    @Operation(
            summary = "Retrieve available API resources",
            description = "Returns a list of available endpoints in the API.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful retrieval of API resources",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = HashMap.class),
                                    examples = @ExampleObject(
                                            name = "API Resources Example",
                                            summary = "Example of a successful response for API resources",
                                            value = """
                                                    {
                                                        "users": "/users",
                                                        "auth": "/auth",
                                                        "topics": "/topics",
                                                        "responses": "/responses",
                                                        "courses": "/courses"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @GetMapping("/")
    public ResponseEntity<Map<String, String>> rootController() {
        Map<String, String> resources = new HashMap<>();
        resources.put("users", "/users");
        resources.put("auth", "/auth");
        resources.put("topics", "/topics");
        resources.put("responses", "/responses");
        resources.put("courses", "/courses");
        return ResponseEntity.ok(resources);
    }

}

