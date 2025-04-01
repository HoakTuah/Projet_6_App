package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.services.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * REST controller for managing topics in the forum system.
 * Provides endpoints for topic operations and subscription management.
 * 
 * Features:
 * <ul>
 * <li>Topic listing and retrieval</li>
 * <li>Topic subscription management</li>
 * <li>User-specific topic subscriptions</li>
 * <li>Error handling for not found scenarios</li>
 * </ul>
 * 
 * Security:
 * <ul>
 * <li>JWT-based authentication required for subscription operations</li>
 * <li>User verification through JWT token</li>
 * <li>Subscription validation</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/topics")
@Tag(name = "Topics", description = "Topics management API")
public class TopicController {

    /**
     * Service for handling topic-related operations.
     * Provides functionality for topic management and subscription handling.
     */
    private final TopicService topicService;

    /**
     * Constructs a TopicController with the required service.
     * 
     * @param topicService Service for topic operations, including subscription
     *                     management
     */
    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    /**
     * Retrieves all topics in the system.
     * Topics are ordered by creation date.
     * 
     * Process:
     * <ul>
     * <li>Retrieves all topics from the database</li>
     * <li>Orders topics by creation date</li>
     * <li>Returns the ordered list of topics</li>
     * </ul>
     *
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>List of TopicResponse objects (200 OK)</li>
     *         <li>Error response for server issues (500 Internal Server Error)</li>
     *         </ul>
     */
    @Operation(summary = "Get all topics", description = "Returns all topics ordered by creation date. " +
            "Topics include title, description, and subscriber count.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved topics", content = @Content(schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred while processing the request", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<TopicResponse>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    /**
     * Subscribes the current user to a specific topic.
     * Requires authentication. The user ID is extracted from the JWT token.
     * 
     * Process:
     * <ul>
     * <li>Validates topic existence</li>
     * <li>Checks for existing subscription</li>
     * <li>Adds user to topic subscribers</li>
     * <li>Returns updated topic information</li>
     * </ul>
     *
     * @param topicId The ID of the topic to subscribe to
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>TopicResponse with updated topic details (200 OK)</li>
     *         <li>Error response for topic not found (404 Not Found)</li>
     *         <li>Error response for existing subscription (400 Bad Request)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         <li>Error response for server issues (500 Internal Server Error)</li>
     *         </ul>
     */
    @Operation(summary = "Subscribe to a topic", description = "Subscribes the current user to the specified topic. " +
            "Requires authentication. User will receive updates for this topic.", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully subscribed to topic", content = @Content(schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic not found with the specified ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Already subscribed to this topic", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred while processing the request", content = @Content)
    })
    @PostMapping("/{topicId}/subscribe")
    public ResponseEntity<TopicResponse> subscribeTopic(
            @Parameter(description = "ID of the topic to subscribe to", required = true) @PathVariable Integer topicId) {
        return ResponseEntity.ok(topicService.subscribeTopic(topicId));
    }

    /**
     * Unsubscribes the current user from a specific topic.
     * Requires authentication. The user ID is extracted from the JWT token.
     * 
     * Process:
     * <ul>
     * <li>Validates topic existence</li>
     * <li>Checks for existing subscription</li>
     * <li>Removes user from topic subscribers</li>
     * <li>Returns updated topic information</li>
     * </ul>
     *
     * @param topicId The ID of the topic to unsubscribe from
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>TopicResponse with updated topic details (200 OK)</li>
     *         <li>Error response for topic not found (404 Not Found)</li>
     *         <li>Error response for no existing subscription (400 Bad
     *         Request)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         <li>Error response for server issues (500 Internal Server Error)</li>
     *         </ul>
     */
    @Operation(summary = "Unsubscribe from a topic", description = "Unsubscribes the current user from the specified topic. "
            +
            "Requires authentication. User will no longer receive updates for this topic.", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully unsubscribed from topic", content = @Content(schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "404", description = "Topic not found with the specified ID", content = @Content),
            @ApiResponse(responseCode = "400", description = "Not subscribed to this topic", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred while processing the request", content = @Content)
    })
    @DeleteMapping("/{topicId}/unsubscribe")
    public ResponseEntity<TopicResponse> unsubscribeTopic(
            @Parameter(description = "ID of the topic to unsubscribe from", required = true) @PathVariable Integer topicId) {
        return ResponseEntity.ok(topicService.unsubscribeTopic(topicId));
    }

    /**
     * Retrieves all topics that the current user is subscribed to.
     * Requires authentication. The user ID is extracted from the JWT token.
     * 
     * Process:
     * <ul>
     * <li>Extracts user information from JWT token</li>
     * <li>Retrieves user's subscribed topics</li>
     * <li>Returns the list of subscribed topics</li>
     * </ul>
     *
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>List of TopicResponse objects (200 OK)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         <li>Error response for server issues (500 Internal Server Error)</li>
     *         </ul>
     */
    @Operation(summary = "Get subscribed topics", description = "Returns all topics the current user is subscribed to. "
            +
            "Requires authentication. Topics are ordered by creation date.", security = @SecurityRequirement(name = "Bearer Authentication"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved subscribed topics", content = @Content(schema = @Schema(implementation = TopicResponse.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred while processing the request", content = @Content)
    })
    @GetMapping("/subscribed")
    public ResponseEntity<List<TopicResponse>> getSubscribedTopics() {
        return ResponseEntity.ok(topicService.getSubscribedTopics());
    }
}