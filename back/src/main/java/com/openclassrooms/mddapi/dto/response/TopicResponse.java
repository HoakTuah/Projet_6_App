package com.openclassrooms.mddapi.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TopicResponse {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int subscriberCount;
    private String message;
    private boolean success;
}