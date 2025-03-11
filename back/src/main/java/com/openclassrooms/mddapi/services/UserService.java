package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dto.LoginRequest;
import com.openclassrooms.mddapi.dto.LoginResponse;
import com.openclassrooms.mddapi.dto.RegisterRequest;
import com.openclassrooms.mddapi.dto.RegisterResponse;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
}