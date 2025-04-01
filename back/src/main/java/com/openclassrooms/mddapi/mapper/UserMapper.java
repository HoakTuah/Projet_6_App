package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.common.UserDto;
import com.openclassrooms.mddapi.dto.request.RegisterRequest;
import com.openclassrooms.mddapi.dto.response.LoginResponse;
import com.openclassrooms.mddapi.dto.response.RegisterResponse;
import com.openclassrooms.mddapi.dto.response.UpdateProfileResponse;
import com.openclassrooms.mddapi.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.time.LocalDateTime;

/**
 * Mapper interface for User entity and related DTOs.
 * Provides methods to convert between User entities and various DTOs used in
 * the application.
 * Uses MapStruct for automatic implementation generation.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Component
@Mapper(componentModel = "spring", imports = { Optional.class, LocalDateTime.class })
public interface UserMapper {

        /**
         * Converts a RegisterRequest DTO to a User entity.
         * Sets the creation timestamp and ignores certain fields that are managed
         * separately.
         *
         * @param registerRequest The registration request DTO
         * @return A new User entity with the registration data
         */
        @Mappings({
                        @Mapping(target = "id", ignore = true),
                        @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
                        @Mapping(source = "username", target = "username"),
                        @Mapping(source = "email", target = "email"),
                        @Mapping(source = "password", target = "password"),
                        @Mapping(target = "subscribedTopics", ignore = true)
        })
        User toUser(RegisterRequest registerRequest);

        /**
         * Creates a RegisterResponse DTO from user registration data.
         * Includes user information, JWT token, and operation status.
         *
         * @param user     The registered user
         * @param jwtToken The generated JWT token
         * @param message  The response message
         * @param success  The operation success status
         * @return A RegisterResponse DTO containing the registration result
         */
        @Mappings({
                        @Mapping(source = "user.id", target = "userId"),
                        @Mapping(source = "user.usernameDisplay", target = "username"),
                        @Mapping(source = "user.email", target = "email"),
                        @Mapping(source = "jwtToken", target = "token"),
                        @Mapping(source = "message", target = "message"),
                        @Mapping(source = "success", target = "success")
        })
        RegisterResponse toRegisterResponse(User user, String jwtToken, String message, boolean success);

        /**
         * Converts a User entity to a UserDto.
         * Includes all relevant user information including subscribed topics.
         *
         * @param user The user entity to convert
         * @return A UserDto containing the user's information
         */
        @Mappings({
                        @Mapping(source = "id", target = "id"),
                        @Mapping(source = "username", target = "username"),
                        @Mapping(source = "email", target = "email"),
                        @Mapping(source = "createdAt", target = "createdAt"),
                        @Mapping(source = "subscribedTopics", target = "subscribedTopics")
        })
        UserDto toDto(User user);

        /**
         * Creates a LoginResponse DTO from user login data.
         * Includes user information, JWT token, and operation status.
         *
         * @param user     The authenticated user
         * @param jwtToken The generated JWT token
         * @param message  The response message
         * @param success  The operation success status
         * @return A LoginResponse DTO containing the login result
         */
        @Mappings({
                        @Mapping(source = "user.id", target = "userId"),
                        @Mapping(source = "user.usernameDisplay", target = "username"),
                        @Mapping(source = "user.email", target = "email"),
                        @Mapping(source = "jwtToken", target = "token"),
                        @Mapping(source = "message", target = "message"),
                        @Mapping(source = "success", target = "success"),
                        @Mapping(target = "subscribedTopics", ignore = true)
        })
        LoginResponse toLoginResponse(User user, String jwtToken, String message, boolean success);

        /**
         * Creates an UpdateProfileResponse DTO from user profile update data.
         * Includes updated user information and operation status.
         *
         * @param user    The updated user
         * @param message The response message
         * @param success The operation success status
         * @param token   The updated JWT token
         * @return An UpdateProfileResponse DTO containing the update result
         */
        @Mappings({
                        @Mapping(source = "user.id", target = "userId"),
                        @Mapping(source = "user.usernameDisplay", target = "username"),
                        @Mapping(source = "user.email", target = "email"),
                        @Mapping(source = "token", target = "token"),
                        @Mapping(source = "message", target = "message"),
                        @Mapping(source = "success", target = "success")
        })
        UpdateProfileResponse toUpdateProfileResponse(User user, String message, boolean success, String token);
}