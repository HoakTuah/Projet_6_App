package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating a new post.
 * Contains the necessary information to create a post in the system.
 * 
 * Features:
 * <ul>
 * <li>Topic association validation</li>
 * <li>Title and content validation</li>
 * <li>Automatic timestamp generation</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in post creation endpoints</li>
 * <li>Validates post data before processing</li>
 * <li>Ensures proper topic association</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class PostRequest {
    /**
     * The topic ID under which the post will be created.
     */
    @NotNull(message = "Topic ID is required")
    private Integer topicId;

    /**
     * The title of the post.
     */
    @NotBlank(message = "Title is required")
    private String title;

    /**
     * The main content of the post.
     */
    @NotBlank(message = "Content is required")
    private String content;
}