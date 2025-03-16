package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.LoginResponse;
import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.dto.RegisterResponse;
import com.openclassrooms.mddapi.dto.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.UpdateProfileResponse;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.Optional;

/**
 * Service class that handles user-related operations.
 * Provides functionality for user authentication and registration.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */

@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a UserService with the required repository.
     * 
     * @param userRepository Repository for accessing user data
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user based on username/email and password.
     * Determines whether the provided identifier is an email or username
     * and searches the database accordingly.
     * 
     * @param loginRequest DTO containing login credentials
     * @return LoginResponse with authentication result and user information if
     *         successful
     */

    public LoginResponse login(LoginRequest loginRequest) {
        // Check if the input is an email or username
        boolean isEmail = loginRequest.getUsername().contains("@");

        Optional<User> userOptional;
        if (isEmail) {
            userOptional = userRepository.findByEmail(loginRequest.getUsername());
        } else {
            userOptional = userRepository.findByUsername(loginRequest.getUsername());
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPassword().equals(loginRequest.getPassword())) {
                return new LoginResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        "Login successful",
                        true);
            } else {
                return new LoginResponse(
                        null,
                        null,
                        null,
                        "Invalid password",
                        false);
            }
        } else {
            return new LoginResponse(
                    null,
                    null,
                    null,
                    "User not found",
                    false);
        }
    }

    /**
     * Registers a new user in the system.
     * Validates that the username and email are not already in use
     * before creating the new user account.
     * 
     * @param registerRequest DTO containing registration information
     * @return RegisterResponse with registration result and user information if
     *         successful
     */

    public RegisterResponse register(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return new RegisterResponse(
                    null,
                    null,
                    null,
                    "Username already exists",
                    false);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new RegisterResponse(
                    null,
                    null,
                    null,
                    "Email already exists",
                    false);
        }

        // Create new user
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setPassword(registerRequest.getPassword());
        newUser.setCreatedAt(LocalDateTime.now());

        // Save user to database
        User savedUser = userRepository.save(newUser);

        // Return success response
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                "Registration successful",
                true);
    }

    /**
     * Updates a user's profile information.
     * Allows changing username, email, and/or password.
     * Validates that the new email (if provided) is not already in use by another
     * user.
     * 
     * @param userId        The ID of the user to update
     * @param updateRequest DTO containing the fields to update
     * @return UpdateProfileResponse with update result and updated user information
     */
    public UpdateProfileResponse updateProfile(Integer userId, UpdateProfileRequest updateRequest) {
        // Find the user by ID
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return new UpdateProfileResponse(
                    null,
                    null,
                    null,
                    "User not found",
                    false);
        }

        User user = userOptional.get();
        boolean changes = false;

        // Check and update email if provided
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            // Check if the new email is different from the current one
            if (!updateRequest.getEmail().equals(user.getEmail())) {
                // Check if the new email is already in use by another user
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    return new UpdateProfileResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            "Email already in use by another user",
                            false);
                }

                user.setEmail(updateRequest.getEmail());
                changes = true;
            }
        }

        // Check and update username if provided
        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            // Check if the new username is different from the current one
            if (!updateRequest.getUsername().equals(user.getUsername())) {
                // Check if the new username is already in use by another user
                if (userRepository.existsByUsername(updateRequest.getUsername())) {
                    return new UpdateProfileResponse(
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            "Username already in use by another user",
                            false);
                }

                user.setUsername(updateRequest.getUsername());
                changes = true;
            }
        }

        // Check and update password if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            // In a real application, you would hash the password here
            user.setPassword(updateRequest.getPassword());
            changes = true;
        }

        // If no changes were made, return a message
        if (!changes) {
            return new UpdateProfileResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    "No changes were made to the profile",
                    true);
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Return success response
        return new UpdateProfileResponse(
                updatedUser.getId(),
                updatedUser.getUsername(),
                updatedUser.getEmail(),
                "Profile updated successfully",
                true);
    }
}