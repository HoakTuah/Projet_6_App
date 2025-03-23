package com.openclassrooms.mddapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for creating a new post.
 * Contains the necessary information to create a post in the system.
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