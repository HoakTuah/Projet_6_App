package com.openclassrooms.mddapi.dto.common;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    private Integer postId;
    private Integer userId;
    private String username;
    private String content;
    private LocalDateTime commentedAt;
}