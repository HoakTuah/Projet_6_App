package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.dto.request.PostRequest;
import com.openclassrooms.mddapi.services.PostService;
import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
import com.openclassrooms.mddapi.exceptions.TopicNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
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
 * REST controller for managing posts.
 * Provides endpoints for creating and managing posts in the system.
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

    @Autowired
    private PostService postService;

    /**
     * Creates a new post in the system.
     * Requires authentication. The user ID is extracted from the JWT token.
     *
     * @param request the post creation request containing title, content, and topic
     *                ID
     * @return ResponseEntity containing the created post details
     * @throws UsernameNotFoundException if the authenticated user cannot be found
     * @throws TopicNotFoundException    if the referenced topic does not exist
     */
    @Operation(summary = "Create a new post", description = "Creates a new post with the provided title and content under the specified topic. "
            +
            "Requires user authentication. The post will be associated with the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Post created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Topic not found or User not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostRequest request) {
        PostDto createdPost = postService.createPost(request);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * Retrieves all posts in the system.
     * Posts are ordered by publication date (most recent first).
     *
     * @return List of all posts
     */
    @Operation(summary = "Get all posts", description = "Retrieves all posts ordered by publication date (most recent first)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved posts", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "401", description = "Not authenticated", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Retrieves a specific post by its ID.
     *
     * @param id The ID of the post to retrieve
     * @return The requested post
     * @throws PostNotFoundException if the post is not found with the given ID
     */
    @Operation(summary = "Get post by ID", description = "Retrieves a specific post using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the post", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer id) {
        PostDto post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }
}