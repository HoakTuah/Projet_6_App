package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.dto.request.PostRequest;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
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
import java.util.List;
import jakarta.validation.Valid;

/**
 * REST controller for managing posts in the forum system.
 * Provides endpoints for creating, retrieving, and managing posts within
 * topics.
 * 
 * Features:
 * <ul>
 * <li>Post creation with user authentication</li>
 * <li>Retrieval of posts by ID or all posts</li>
 * <li>Topic-based post organization</li>
 * <li>Input validation</li>
 * <li>Error handling for not found scenarios</li>
 * </ul>
 * 
 * Security:
 * <ul>
 * <li>JWT-based authentication required for post creation</li>
 * <li>User verification through JWT token</li>
 * <li>Input validation using Jakarta Validation</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
@Tag(name = "Post", description = "Post management APIs")
public class PostController {

    /**
     * Service for handling post-related operations.
     * Provides functionality for post creation, retrieval, and management.
     */
    @Autowired
    private PostService postService;

    /**
     * Creates a new post in the system.
     * Requires authentication. The user ID is extracted from the JWT token.
     * 
     * Process:
     * <ul>
     * <li>Validates post content and topic existence</li>
     * <li>Extracts user information from JWT token</li>
     * <li>Creates and persists the post</li>
     * <li>Returns the created post details</li>
     * </ul>
     *
     * @param request The post creation request containing:
     *                <ul>
     *                <li>Title of the post</li>
     *                <li>Content of the post</li>
     *                <li>ID of the topic to post in</li>
     *                </ul>
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>PostDto with created post details (201 Created)</li>
     *         <li>Error response for invalid request (400 Bad Request)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         <li>Error response for not found resources (404 Not Found)</li>
     *         </ul>
     * @throws UsernameNotFoundException if the authenticated user cannot be found
     * @throws TopicNotFoundException    if the referenced topic does not exist
     */
    @Operation(summary = "Create a new post", description = "Creates a new post with the provided title and content under the specified topic. "
            +
            "Requires user authentication. The post will be associated with the authenticated user.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data - Title, content, or topic ID is invalid", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required or invalid token", content = @Content),
            @ApiResponse(responseCode = "404", description = "Topic not found or User not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PostDto> createPost(
            @Parameter(description = "Post creation request", required = true) @Valid @RequestBody PostRequest request) {
        PostDto createdPost = postService.createPost(request);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * Retrieves all posts in the system.
     * Posts are ordered by publication date (most recent first).
     * 
     * Process:
     * <ul>
     * <li>Retrieves all posts from the database</li>
     * <li>Orders posts by publication date (descending)</li>
     * <li>Returns the ordered list of posts</li>
     * </ul>
     *
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>List of PostDto objects (200 OK)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         </ul>
     */
    @Operation(summary = "Get all posts", description = "Retrieves all posts ordered by publication date (most recent first). "
            +
            "Posts include user information, topic details, and publication timestamp.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Retrieves a specific post by its ID.
     * 
     * Process:
     * <ul>
     * <li>Validates post existence</li>
     * <li>Retrieves post details</li>
     * <li>Returns the requested post</li>
     * </ul>
     *
     * @param id The ID of the post to retrieve
     * @return ResponseEntity containing:
     *         <ul>
     *         <li>PostDto with post details (200 OK)</li>
     *         <li>Error response for post not found (404 Not Found)</li>
     *         <li>Error response for unauthorized access (401 Unauthorized)</li>
     *         </ul>
     * @throws PostNotFoundException if the post is not found with the given ID
     */
    @Operation(summary = "Get post by ID", description = "Retrieves a specific post using its ID. " +
            "Returns detailed post information including author, topic, and comments.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found with the specified ID", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated - Valid JWT token required", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(
            @Parameter(description = "ID of the post to retrieve", required = true) @PathVariable Integer id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
}