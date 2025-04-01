package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating or updating comments on posts.
 * Contains the necessary information to create a new comment or update an
 * existing one.
 * 
 * Features:
 * <ul>
 * <li>Validation constraints for required fields</li>
 * <li>Association with parent post</li>
 * <li>Content validation</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in comment creation endpoints</li>
 * <li>Validates comment data before processing</li>
 * <li>Ensures proper post association</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class CommentRequest {
    @NotNull(message = "Post ID is required")
    private Integer postId;

    @NotBlank(message = "Content is required")
    private String content;
}