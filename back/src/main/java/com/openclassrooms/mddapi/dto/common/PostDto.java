package com.openclassrooms.mddapi.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for post information.
 * Represents a post in the system with all its associated data.
 * Used for sending post data to clients.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class PostDto {
    private Integer id;
    private Integer userId;
    private String username;
    private Integer topicId;
    private String topicTitle;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
}