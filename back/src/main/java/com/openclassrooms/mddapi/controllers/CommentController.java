package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.common.CommentDto;
import com.openclassrooms.mddapi.dto.request.CommentRequest;
import com.openclassrooms.mddapi.services.CommentService;
import com.openclassrooms.mddapi.exceptions.PostNotFoundException;
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

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for managing comments.
 * Provides endpoints for creating and retrieving comments on posts.
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

    @Autowired
    private CommentService commentService;

    /**
     * Creates a new comment on a post.
     * Requires authentication. The user ID is extracted from the JWT token.
     *
     * @param request the comment creation request containing content and post ID
     * @return ResponseEntity containing the created comment details
     * @throws UsernameNotFoundException if the authenticated user cannot be found
     * @throws PostNotFoundException     if the referenced post does not exist
     */
    @Operation(summary = "Create a new comment", description = "Creates a new comment with the provided content under the specified post. "
            +
            "Requires user authentication. The comment will be associated with the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Authentication required", content = @Content),
            @ApiResponse(responseCode = "404", description = "Post not found or User not found", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentRequest request) {
        CommentDto createdComment = commentService.createComment(request);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    /**
     * Retrieves all comments for a specific post.
     *
     * @param postId The ID of the post to retrieve comments for
     * @return List of comments for the specified post
     * @throws PostNotFoundException if the post is not found with the given ID
     */
    @Operation(summary = "Get all comments for a post", description = "Retrieves all comments associated with a specific post, ordered by creation date")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved comments", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))),
            @ApiResponse(responseCode = "404", description = "Post not found", content = @Content),
            @ApiResponse(responseCode = "401", description = "Not authenticated", content = @Content)
    })
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable Integer postId) {
        List<CommentDto> comments = commentService.getPostComments(postId);
        return ResponseEntity.ok(comments);
    }
}