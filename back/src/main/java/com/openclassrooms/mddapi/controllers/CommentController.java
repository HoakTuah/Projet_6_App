package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.common.CommentDto;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing comments on posts.
 * Provides endpoints for creating and retrieving comments within the forum
 * system.
 * 
 * Features:
 * <ul>
 * <li>Comment creation with user authentication</li>
 * <li>Retrieval of comments by post</li>
 * <li>Input validation</li>
 * <li>Error handling for not found scenarios</li>
 * </ul>
 * 
 * Security:
 * <ul>
 * <li>JWT-based authentication required for comment creation</li>
 * <li>User verification through JWT token</li>
 * <li>Input validation using Jakarta Validation</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
@Tag(name = "Comment", description = "Comment management APIs")
public class CommentController {

    /**
     * Service for handling comment-related operations.
     * Provides functionality for comment creation and retrieval.
     */
    @Autowired
    private CommentService commentService;

    /**
     * Creates a new comment on a post.
     * Requires authentication. The user ID is extracted from the JWT token.
     * 
     * Process:
     * <ul>
     * <li>Validates comment content and post existence</li>
     * <li>Extracts user information from JWT token</li>
     * <li>Creates and persists the comment</li>
     * <li>Returns the created comment details</li>
     * </ul>
     *
     * @param request The comment creation request containing:
     *                <ul>
     *                <li>Content of the comment</li>
     *                <li>ID of the post to comment on</li>
     *                </ul>
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>CommentDto with created comment details (201 Created)</li>
     *         <li>Error response for invalid request (400 Bad Request)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         <li>Error response for not found resources (404 Not Found)</li>
     *         </ul>
     * @throws UsernameNotFoundException if the authenticated user cannot be found
     * @throws PostNotFoundException     if the referenced post does not exist
     */
    @Operation(summary = "Create a new comment", description = "Creates a new comment with the provided content under the specified post. "
            +
            "Requires user authentication. The comment will be associated with the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data - Content or post ID is invalid", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required or invalid token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found or User not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @Parameter(description = "Comment creation request", required = true) @Valid @RequestBody CommentRequest request) {
        CommentDto createdComment = commentService.createComment(request);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    /**
     * Retrieves all comments for a specific post.
     * Comments are ordered by creation date.
     * 
     * Process:
     * <ul>
     * <li>Validates post existence</li>
     * <li>Retrieves all comments for the post</li>
     * <li>Orders comments by creation date</li>
     * <li>Returns the list of comments</li>
     * </ul>
     *
     * @param postId The ID of the post to retrieve comments for
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>List of CommentDto objects (200 OK)</li>
     *         <li>Error response for post not found (404 Not Found)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         </ul>
     * @throws PostNotFoundException if the post is not found with the given ID
     */
    @Operation(summary = "Get all comments for a post", description = "Retrieves all comments associated with a specific post, ordered by creation date. "
            +
            "Comments include user information and creation timestamp.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found with the specified ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content)
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getPostComments(
            @Parameter(description = "ID of the post to retrieve comments for", required = true) @PathVariable Integer postId) {
        List<CommentDto> comments = commentService.getPostComments(postId);
        return ResponseEntity.ok(comments);
    }
}