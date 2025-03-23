package com.openclassrooms.mddapi.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDto {
    private Integer id;
    private Integer userId;
    private Integer topicId;
    private String title;
    private String content;
    private LocalDateTime publishedAt;
}