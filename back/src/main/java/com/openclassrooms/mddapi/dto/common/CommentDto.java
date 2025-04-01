package com.openclassrooms.mddapi.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for comment information.
 * Represents a comment in the system with all its associated data.
 * Used for sending comment data to clients.
 * 
 * Features:
 * <ul>
 * <li>Complete comment information</li>
 * <li>User and post associations</li>
 * <li>Timestamp tracking</li>
 * <li>Content management</li>
 * </ul>
 * 
 * Usage:
 * <ul>
 * <li>Used in comment-related API responses</li>
 * <li>Transfers comment data to clients</li>
 * <li>Enables comment display and management</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
public class CommentDto {
    /**
     * The unique identifier of the comment.
     * 
     * Features:
     * <ul>
     * <li>Primary key for comment identification</li>
     * <li>Used for comment references</li>
     * <li>Required for all operations</li>
     * </ul>
     */
    private Integer id;

    /**
     * The ID of the post to which this comment belongs.
     * 
     * Features:
     * <ul>
     * <li>Post association</li>
     * <li>Hierarchical relationship</li>
     * <li>Required for comment context</li>
     * </ul>
     */
    private Integer postId;

    /**
     * The ID of the user who created the comment.
     * 
     * Features:
     * <ul>
     * <li>Author identification</li>
     * <li>User association</li>
     * <li>Required for attribution</li>
     * </ul>
     */
    private Integer userId;

    /**
     * The username of the comment author.
     * 
     * Features:
     * <ul>
     * <li>Author display name</li>
     * <li>User identification</li>
     * <li>Required for display</li>
     * </ul>
     */
    private String username;

    /**
     * The content of the comment.
     * 
     * Features:
     * <ul>
     * <li>Comment text</li>
     * <li>Rich text support</li>
     * <li>Content validation</li>
     * </ul>
     */
    private String content;

    /**
     * The timestamp when the comment was created.
     * 
     * Features:
     * <ul>
     * <li>Creation time tracking</li>
     * <li>Used for sorting</li>
     * <li>Read-only after creation</li>
     * </ul>
     */
    private LocalDateTime commentedAt;
}