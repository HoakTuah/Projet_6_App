package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.LoginResponse;
import com.openclassrooms.mddapi.dto.response.RegisterResponse;
import com.openclassrooms.mddapi.dto.response.UpdateProfileResponse;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication-related requests.
 * Provides endpoints for user login and registration.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {

    /**
     * Service for handling user-related operations.
     */

    private final UserService userService;

    /**
     * Constructs an AuthController with the required service.
     * 
     * @param userService Service for user operations
     */

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles user login requests.
     * Authenticates a user based on the provided credentials.
     * 
     * @param loginRequest DTO containing login credentials
     * @return ResponseEntity with login result and appropriate HTTP status code
     */

    @Operation(summary = "User login", description = "Authenticate a user with username/email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = userService.login(loginRequest);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    /**
     * Handles user registration requests.
     * Creates a new user account based on the provided information.
     * 
     * @param registerRequest DTO containing registration information
     * @return ResponseEntity with registration result and appropriate HTTP status
     *         code
     */

    @Operation(summary = "User registration", description = "Register a new user with email, username, and password")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully registered", content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
            @ApiResponse(responseCode = "400", description = "Registration failed", content = @Content(schema = @Schema(implementation = RegisterResponse.class)))
    })
    @PostMapping("/register")

    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        RegisterResponse response = userService.register(registerRequest);

        if (response.isSuccess()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Handles user profile update requests.
     * Updates a user's profile information based on the provided data.
     * 
     * @param userId        The ID of the user to update
     * @param updateRequest DTO containing the fields to update
     * @return ResponseEntity with update result and appropriate HTTP status code
     */
    @Operation(summary = "Update user profile", description = "Update a user's profile information (username, email, password)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile successfully updated", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update failed", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
    })

    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            @PathVariable Integer userId,
            @RequestBody UpdateProfileRequest updateRequest) {

        UpdateProfileResponse response = userService.updateProfile(userId, updateRequest);

        if (!response.isSuccess()) {
            if (response.getMessage().equals("User not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        }

        return ResponseEntity.ok(response);
    }
}