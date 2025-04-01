package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entity representing a comment in the system.
 * A comment is a user's response to a post, containing content and metadata.
 * Each comment belongs to a specific post and is authored by a user.
 * 
 * Features:
 * <ul>
 * <li>Comment content management</li>
 * <li>Association with posts and users</li>
 * <li>Timestamp tracking</li>
 * </ul>
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "commented_at")
    private LocalDateTime commentedAt;
}