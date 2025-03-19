package com.openclassrooms.mddapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Entity representing a Topic in the system.
 * Topics contain a title, content, and creation timestamp.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "topics")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Topic entity representing a discussion topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the topic")
    private Integer id;

    @Column(unique = true, nullable = false)
    @Schema(description = "Title of the topic", required = true)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Content of the topic", required = true)
    private String content;

    @Column(name = "created_at")
    @Schema(description = "Timestamp when the topic was created")
    private LocalDateTime createdAt;
}