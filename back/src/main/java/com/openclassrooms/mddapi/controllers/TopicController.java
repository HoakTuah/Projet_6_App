package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.TopicRequest;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Topics.
 * Provides endpoints for topic operations.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/topics")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Topics", description = "Topics management API")
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * GET /api/topics : Get all topics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of topics in the
     *         body
     */
    @Operation(summary = "Get all topics", description = "Returns all topics ordered by creation date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved topics", content = @Content(schema = @Schema(implementation = TopicRequest.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TopicRequest>> getAllTopics() {
        List<TopicRequest> topics = topicService.getAllTopics();
        return ResponseEntity.ok(topics);
    }
}