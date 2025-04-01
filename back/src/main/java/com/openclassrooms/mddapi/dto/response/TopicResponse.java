package com.openclassrooms.mddapi.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Response Data Transfer Object for topic operations.
 * Used to send topic information and operation results back to clients.
 * 
 * Features:
 * <ul>
 * <li>Complete topic information</li>
 * <li>Operation status tracking</li>
 * <li>Subscriber count tracking</li>
 * <li>Timestamp management</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in topic-related API responses</li>
 * <li>Provides operation feedback to clients</li>
 * <li>Enables topic data visualization</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class TopicResponse {
    /**
     * The unique identifier of the topic.
     */
    private Integer id;

    /**
     * The title of the topic.
     */
    private String title;

    /**
     * The description or content of the topic.
     */
    private String content;

    /**
     * The date and time when the topic was created.
     */
    private LocalDateTime createdAt;

    /**
     * The number of users subscribed to this topic.
     */
    private int subscriberCount;

    /**
     * A message providing feedback about the operation performed.
     */
    private String message;

    /**
     * Indicates whether the operation was successful.
     */
    private boolean success;
}