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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.Security.JwtService;
import com.openclassrooms.mddapi.Security.PasswordValidator;
import com.openclassrooms.mddapi.exceptions.UserAlreadyExistsException;
import com.openclassrooms.mddapi.exceptions.InvalidPasswordException;
import com.openclassrooms.mddapi.exceptions.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class that handles user-related operations with security
 * implementation.
 * Provides comprehensive functionality for user authentication, registration,
 * profile management,
 * and token handling. Implements UserDetailsService for Spring Security
 * integration.
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
    private final JwtService jwtService;

    /**
     * Constructs a UserService with required dependencies.
     * 
     * @param userRepository  Repository for user data operations
     * @param passwordEncoder Encoder for password hashing and verification
     * @param userMapper      Mapper for DTO conversions
     * @param jwtService      Service for JWT token generation and management
     */
    @Autowired
    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    /**
     * Loads a user by username or email for Spring Security authentication.
     * Supports both username and email-based authentication.
     * 
     * @param username The username or email to search for
     * @return UserDetails object for Spring Security
     * @throws UsernameNotFoundException if user not found with the given
     *                                   username/email
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional;
        if (username.contains("@")) {
            userOptional = userRepository.findByEmail(username);
        } else {
            userOptional = userRepository.findByUsername(username);
        }

        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException(
                        "L'utilisateur n'existe pas avec le nom d'utilisateur/email: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    /**
     * Authenticates a user and generates a JWT token.
     * Supports both email and username login.
     * Validates password and handles authentication errors.
     * 
     * @param loginRequest DTO containing login credentials
     * @return LoginResponse with authentication result and JWT token
     * @throws UsernameNotFoundException if user not found
     * @throws InvalidPasswordException  if password is incorrect
     */
    public LoginResponse login(LoginRequest loginRequest) {
        boolean isEmail = loginRequest.getUsername().contains("@");

        User user = (isEmail ? userRepository.findByEmail(loginRequest.getUsername())
                : userRepository.findByUsername(loginRequest.getUsername()))
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur n'existe pas"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Mot de passe incorrect");
        }

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);
        return userMapper.toLoginResponse(user, token, "Connexion réussie", true);
    }

    /**
     * Registers a new user with encoded password.
     * Validates username and email uniqueness.
     * Performs password validation and encoding.
     * 
     * @param registerRequest DTO containing registration information
     * @return RegisterResponse with registration result and JWT token
     * @throws UserAlreadyExistsException if email or username is already taken
     * @throws InvalidPasswordException   if password validation fails
     */
    public RegisterResponse register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new UserAlreadyExistsException("Email déjà enregistré");
        }

        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistsException("Nom d'utilisateur déjà pris");
        }

        String validationError = PasswordValidator.validate(registerRequest.getPassword());
        if (validationError != null) {
            throw new InvalidPasswordException(validationError);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        UserDetails userDetails = loadUserByUsername(savedUser.getUsername());
        String token = jwtService.generateToken(userDetails);

        return userMapper.toRegisterResponse(savedUser, token, "Inscription réussie", true);
    }

    /**
     * Updates a user's profile information.
     * Handles username, email, and password updates with validation.
     * Generates new token if email is changed.
     * 
     * @param userId        The ID of the user to update
     * @param updateRequest DTO containing update information
     * @return UpdateProfileResponse with update result and new token if email
     *         changed
     * @throws UserNotFoundException      if user not found
     * @throws UserAlreadyExistsException if new email/username is already taken
     * @throws InvalidPasswordException   if new password validation fails
     */
    public UpdateProfileResponse updateProfile(Integer userId, UpdateProfileRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur n'existe pas avec l'id: " + userId));

        boolean changes = false;
        String oldEmail = user.getEmail();
        boolean emailChanged = false;

        // Check and update email if provided
        if (updateRequest.getEmail() != null && !updateRequest.getEmail().isEmpty()) {
            if (!updateRequest.getEmail().equals(oldEmail)) {
                if (userRepository.existsByEmail(updateRequest.getEmail())) {
                    throw new UserAlreadyExistsException("Email déjà utilisé par un autre utilisateur");
                }
                user.setEmail(updateRequest.getEmail());
                changes = true;
                emailChanged = true;
            }
        }

        // Check and update username if provided
        if (updateRequest.getUsername() != null && !updateRequest.getUsername().isEmpty()) {
            if (!updateRequest.getUsername().equals(user.getUsername())) {
                if (userRepository.existsByUsername(updateRequest.getUsername())) {
                    throw new UserAlreadyExistsException("Nom d'utilisateur déjà utilisé par un autre utilisateur");
                }
                user.setUsername(updateRequest.getUsername());
                changes = true;
            }
        }

        // Check and update password if provided
        if (updateRequest.getPassword() != null && !updateRequest.getPassword().isEmpty()) {
            String validationError = PasswordValidator.validate(updateRequest.getPassword());
            if (validationError != null) {
                throw new InvalidPasswordException(validationError);
            }
            user.setPassword(passwordEncoder.encode(updateRequest.getPassword()));
            changes = true;
        }

        // If no changes were made, return current state
        if (!changes) {
            return userMapper.toUpdateProfileResponse(user, "Aucune modification n'a été faite sur le profil", true,
                    null);
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // If email changed, generate new token
        if (emailChanged) {
            UserDetails userDetails = loadUserByUsername(updatedUser.getUsername());
            String newToken = jwtService.generateToken(userDetails);
            return userMapper.toUpdateProfileResponse(updatedUser, "Profil mis à jour avec succès", true, newToken);
        }

        return userMapper.toUpdateProfileResponse(updatedUser, "Profil mis à jour avec succès", true, null);
    }

    /**
     * Refreshes the JWT token for the current user.
     * Used when user information changes (e.g., email update).
     * 
     * @param loginRequest DTO containing login information (not used, kept for API
     *                     consistency)
     * @return LoginResponse with new JWT token
     * @throws UsernameNotFoundException if current user is not found
     */

    public LoginResponse refreshToken(LoginRequest loginRequest) {
        // Get current authenticated user's email
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(currentUserEmail)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "L'utilisateur n'existe pas avec l'email: " + currentUserEmail));

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        String newToken = jwtService.generateToken(userDetails);

        return userMapper.toLoginResponse(user, newToken, "Token rafraîchi avec succès", true);
    }
}