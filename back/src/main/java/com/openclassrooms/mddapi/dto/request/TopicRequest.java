package com.openclassrooms.mddapi.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for Topic entities.
 * Used for transferring topic data between layers.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Data
@Schema(description = "Topic Data Transfer Object")
public class TopicRequest {

    @Schema(description = "Unique identifier of the topic")
    private Integer id;

    @Schema(description = "Title of the topic")
    private String title;

    @Schema(description = "Content of the topic")
    private String content;

    @Schema(description = "Timestamp when the topic was created")
    private LocalDateTime createdAt;
}