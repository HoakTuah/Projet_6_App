package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.request.LoginRequest;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.request.UpdateProfileRequest;
import com.openclassrooms.mddapi.dto.response.LoginResponse;
import com.openclassrooms.mddapi.dto.response.RegisterResponse;
import com.openclassrooms.mddapi.dto.response.UpdateProfileResponse;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.mapper.UserMapper;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class that handles user-related operations with security
 * implementation.
 * Provides functionality for user authentication, registration, and profile
 * management.
 * Implements UserDetailsService for Spring Security integration.
 * 
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    /**
     * Constructs a UserService with required dependencies.
     * 
     * @param userRepository  Repository for user data operations
     * @param passwordEncoder Encoder for password hashing
     * @param userMapper      Mapper for DTO conversions
     */

    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Loads a user by username for Spring Security authentication.
     * 
     * @param username The username to search for
     * @return UserDetails object for Spring Security
     * @throws UsernameNotFoundException if user not found
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    /**
     * Authenticates a user and handles password encoding transition.
     * Supports both email and username login.
     * Automatically upgrades plain passwords to BCrypt encoding.
     * 
     * @param loginRequest DTO containing login credentials
     * @return LoginResponse with authentication result
     */

    public LoginResponse login(LoginRequest loginRequest) {
        boolean isEmail = loginRequest.getUsername().contains("@");

        Optional<User> userOptional;
        if (isEmail) {
            userOptional = userRepository.findByEmail(loginRequest.getUsername());
        } else {
            userOptional = userRepository.findByUsername(loginRequest.getUsername());
        }

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if the stored password is BCrypt encoded
            if (!user.getPassword().startsWith("$2a$")) {
                // If not encoded, compare directly first
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    // If match, update to BCrypt encoded password
                    user.setPassword(passwordEncoder.encode(loginRequest.getPassword()));
                    user = userRepository.save(user);
                    return userMapper.toLoginResponse(user, "Login successful", true);
                }
            } else {
                // If already BCrypt encoded, use matches
                if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                    return userMapper.toLoginResponse(user, "Login successful", true);
                }
            }
            return userMapper.toLoginResponse(null, "Invalid password", false);
        }
        return userMapper.toLoginResponse(null, "User not found", false);
    }

    /**
     * Registers a new user with encoded password.
     * Validates username and email uniqueness.
     * 
     * @param registerRequest DTO containing registration information
     * @return RegisterResponse with registration result
     */

    public RegisterResponse register(RegisterRequest registerRequest) {
        // Check if username already exists
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            return userMapper.toRegisterResponse(null, "Username already exists", false);
        }

        // Check if email already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return userMapper.toRegisterResponse(null, "Email already exists", false);
        }

        // Create new user with encoded password
        User newUser = userMapper.toUser(registerRequest);
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setCreatedAt(LocalDateTime.now());

        // Save user to database
        User savedUser = userRepository.save(newUser);

        return userMapper.toRegisterResponse(savedUser, "Registration successful", true);
    }

    /**
     * Updates a user's profile information.
     * Handles username, email, and password updates with validation.
     * Ensures password is properly encoded.
     * 
     * @param userId        The ID of the user to update
     * @param updateRequest DTO containing update information
     * @return UpdateProfileResponse with update result
     */
    public UpdateProfileResponse updateProfile(Integer userId, UpdateProfileRequest updateRequest) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return userMapper.toUpdateProfileResponse(null, "User not found", false);
        }

        User user = userOptional.get();
        boolean changes = false;

        // Check and update email if provided
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            if (!updateRequest.getEmail().equals(user.getEmail())) {
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    return userMapper.toUpdateProfileResponse(user, "Email already in use by another user", false);
                }
                user.setEmail(updateRequest.getEmail());
                changes = true;
            }
        }

        // Check and update username if provided
        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            if (!updateRequest.getUsername().equals(user.getUsername())) {
                if (userRepository.existsByUsername(updateRequest.getUsername())) {
                    return userMapper.toUpdateProfileResponse(user, "Username already in use by another user", false);
                }
                user.setUsername(updateRequest.getUsername());
                changes = true;
            }
        }

        // Check and update password if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            changes = true;
        }

        // If no changes were made, return a message
        if (!changes) {
            return userMapper.toUpdateProfileResponse(user, "No changes were made to the profile", true);
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        return userMapper.toUpdateProfileResponse(updatedUser, "Profile updated successfully", true);
    }
}