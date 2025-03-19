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

@Component
@Mapper(componentModel = "spring", imports = { Optional.class, LocalDateTime.class })
public interface UserMapper {

        @Mappings({
                        @Mapping(target = "id", ignore = true),
                        @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())"),
                        @Mapping(source = "username", target = "username"),
                        @Mapping(source = "email", target = "email"),
                        @Mapping(source = "password", target = "password"),
                        @Mapping(target = "subscribedTopics", ignore = true)
        })
        User toUser(RegisterRequest registerRequest);

        @Mappings({
                        @Mapping(source = "user.id", target = "userId"),
                        @Mapping(source = "user.username", target = "username"),
                        @Mapping(source = "user.email", target = "email"),
                        @Mapping(source = "jwtToken", target = "token"),
                        @Mapping(source = "message", target = "message"),
                        @Mapping(source = "success", target = "success")
        })
        RegisterResponse toRegisterResponse(User user, String jwtToken, String message, boolean success);

        @Mappings({
                        @Mapping(source = "id", target = "id"),
                        @Mapping(source = "username", target = "username"),
                        @Mapping(source = "email", target = "email"),
                        @Mapping(source = "createdAt", target = "createdAt"),
                        @Mapping(source = "subscribedTopics", target = "subscribedTopics")
        })
        UserDto toDto(User user);

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

        @Mappings({
                        @Mapping(source = "user.id", target = "userId"),
                        @Mapping(source = "user.username", target = "username"),
                        @Mapping(source = "user.email", target = "email"),
                        @Mapping(source = "message", target = "message"),
                        @Mapping(source = "success", target = "success")
        })
        UpdateProfileResponse toUpdateProfileResponse(User user, String message, boolean success);
}