package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private Integer userId;
    private String username;
    private String email;
    private String message;
    private boolean success;
}