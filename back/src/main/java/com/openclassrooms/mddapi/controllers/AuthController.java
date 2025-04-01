package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.LoginResponse;
import com.openclassrooms.mddapi.dto.response.RegisterResponse;
import com.openclassrooms.mddapi.dto.response.UpdateProfileResponse;
import com.openclassrooms.mddapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Provides endpoints for user authentication, registration, and profile
 * management.
 * 
 * Features:
 * <ul>
 * <li>User authentication with JWT tokens</li>
 * <li>User registration with validation</li>
 * <li>Profile update management</li>
 * <li>Token refresh functionality</li>
 * </ul>
 * 
 * Security:
 * <ul>
 * <li>JWT-based authentication</li>
 * <li>Password hashing</li>
 * <li>Token expiration</li>
 * <li>Input validation</li>
 * </ul>
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
         * Provides functionality for user management, authentication, and profile
         * updates.
         */
        private final UserService userService;

        /**
         * Constructs an AuthController with the required service.
         * 
         * @param userService Service for user operations, including authentication and
         *                    profile management
         */
        @Autowired
        public AuthController(UserService userService) {
                this.userService = userService;
        }

        /**
         * Handles user login requests.
         * Authenticates a user based on the provided credentials and generates a JWT
         * token.
         * 
         * Process:
         * <ul>
         * <li>Validates user credentials</li>
         * <li>Generates JWT token upon success</li>
         * <li>Returns user information and token</li>
         * </ul>
         *
         * @param loginRequest DTO containing login credentials (username/email and
         *                     password)
         * @return ResponseEntity containing:
         *         <ul>
         *         <li>LoginResponse with user details and token (200 OK)</li>
         *         <li>Error response for failed authentication (401 Unauthorized)</li>
         *         </ul>
         */
        @Operation(summary = "User login", description = "Authenticate a user with username/email and password. Returns a JWT token upon successful authentication.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Successful login", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Authentication failed - Invalid credentials", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
        })
        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(
                        @Parameter(description = "Login credentials", required = true) @RequestBody LoginRequest loginRequest) {
                LoginResponse response = userService.login(loginRequest);
                return response.isSuccess()
                                ? ResponseEntity.ok(response)
                                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        /**
         * Handles user registration requests.
         * Creates a new user account with the provided information.
         * 
         * Process:
         * <ul>
         * <li>Validates registration data</li>
         * <li>Checks for duplicate email/username</li>
         * <li>Creates new user account</li>
         * <li>Generates initial JWT token</li>
         * </ul>
         *
         * @param registerRequest DTO containing registration information (email,
         *                        username, password)
         * @return ResponseEntity containing:
         *         <ul>
         *         <li>RegisterResponse with user details and token (201 Created)</li>
         *         <li>Error response for failed registration (400 Bad Request)</li>
         *         </ul>
         */
        @Operation(summary = "User registration", description = "Register a new user with email, username, and password. Creates account and returns initial JWT token.")
        @ApiResponses({
                        @ApiResponse(responseCode = "201", description = "User successfully registered", content = @Content(schema = @Schema(implementation = RegisterResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Registration failed - Invalid data or duplicate email/username", content = @Content(schema = @Schema(implementation = RegisterResponse.class)))
        })
        @PostMapping("/register")
        public ResponseEntity<RegisterResponse> register(
                        @Parameter(description = "Registration information", required = true) @RequestBody RegisterRequest registerRequest) {
                RegisterResponse response = userService.register(registerRequest);
                return response.isSuccess()
                                ? ResponseEntity.status(HttpStatus.CREATED).body(response)
                                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        /**
         * Handles user profile update requests.
         * Updates user information including email, username, and password.
         * 
         * Process:
         * <ul>
         * <li>Validates update data</li>
         * <li>Checks for duplicate email/username</li>
         * <li>Updates user profile</li>
         * <li>Generates new token if email is changed</li>
         * </ul>
         *
         * @param userId        ID of the user to update
         * @param updateRequest DTO containing updated user information
         * @return ResponseEntity containing:
         *         <ul>
         *         <li>UpdateProfileResponse with updated user details (200 OK)</li>
         *         <li>Error response for failed update (400/401/404)</li>
         *         </ul>
         */
        @Operation(summary = "Update user profile", description = "Update user information including email, username, and password. Generates new token if email is changed.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Profile successfully updated", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid request or duplicate email/username", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or expired token", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = UpdateProfileResponse.class)))
        })
        @PutMapping("/users/{userId}")
        public ResponseEntity<UpdateProfileResponse> updateProfile(
                        @Parameter(description = "User ID", required = true) @PathVariable Integer userId,
                        @Parameter(description = "Updated profile information", required = true) @RequestBody UpdateProfileRequest updateRequest) {
                UpdateProfileResponse response = userService.updateProfile(userId, updateRequest);
                return response.isSuccess()
                                ? ResponseEntity.ok(response)
                                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        /**
         * Handles token refresh requests.
         * Generates a new JWT token for the user.
         * 
         * Process:
         * <ul>
         * <li>Validates user credentials</li>
         * <li>Generates new JWT token</li>
         * <li>Returns updated token</li>
         * </ul>
         *
         * @param loginRequest DTO containing user credentials
         * @return ResponseEntity containing:
         *         <ul>
         *         <li>LoginResponse with new token (200 OK)</li>
         *         <li>Error response for failed refresh (401/404)</li>
         *         </ul>
         */
        @Operation(summary = "Refresh authentication token", description = "Get a new token after email change or when current token is about to expire.")
        @ApiResponses({
                        @ApiResponse(responseCode = "200", description = "Token successfully refreshed", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(schema = @Schema(implementation = LoginResponse.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = LoginResponse.class)))
        })
        @PostMapping("/refresh-token")
        public ResponseEntity<LoginResponse> refreshToken(
                        @Parameter(description = "User credentials", required = true) @RequestBody LoginRequest loginRequest) {
                LoginResponse response = userService.refreshToken(loginRequest);
                return response.isSuccess()
                                ? ResponseEntity.ok(response)
                                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
}