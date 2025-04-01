package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for managing Topics.
 * Provides endpoints for topic operations and subscriptions.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/topics")
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
     * @return the ResponseEntity with status 200 (OK) and the list of topics
     */
    @Operation(summary = "Get all topics", description = "Returns all topics ordered by creation date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved topics", content = @Content(schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    /**
     * POST /api/topics/{topicId}/subscribe : Subscribe to a topic
     *
     * @param topicId the ID of the topic to subscribe to
     * @return the ResponseEntity with status 200 (OK) and the updated topic
     */
    @Operation(summary = "Subscribe to a topic", description = "Subscribes the current user to the specified topic", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully subscribed to topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "400", description = "Already subscribed to topic"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<TopicResponse> subscribeTopic(@PathVariable Integer topicId) {
        return ResponseEntity.ok(topicService.subscribeTopic(topicId));
    }

    /**
     * POST /api/topics/{topicId}/unsubscribe : Unsubscribe from a topic
     *
     * @param topicId the ID of the topic to unsubscribe from
     * @return the ResponseEntity with status 200 (OK) and the updated topic
     */
    @Operation(summary = "Unsubscribe from a topic", description = "Unsubscribes the current user from the specified topic", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed from topic"),
            @ApiResponse(responseCode = "404", description = "Topic not found"),
            @ApiResponse(responseCode = "400", description = "Not subscribed to topic"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<TopicResponse> unsubscribeTopic(@PathVariable Integer topicId) {
        return ResponseEntity.ok(topicService.unsubscribeTopic(topicId));
    }

    /**
     * GET /api/topics/subscribed : Get all topics the current user is subscribed to
     *
     * @return the ResponseEntity with status 200 (OK) and the list of subscribed
     *         topics
     */
    @Operation(summary = "Get subscribed topics", description = "Returns all topics the current user is subscribed to", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscribed topics"),
            @ApiResponse(responseCode = "401", description = "Not authenticated"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/subscribed")
    public ResponseEntity<List<TopicResponse>> getSubscribedTopics() {
        return ResponseEntity.ok(topicService.getSubscribedTopics());
    }
}