package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface TopicMapper {

        @Mapping(source = "topic.id", target = "id")
        @Mapping(source = "topic.title", target = "title")
        @Mapping(source = "topic.content", target = "content")
        @Mapping(source = "topic.createdAt", target = "createdAt")
        @Mapping(source = "topic.subscribers", target = "subscriberCount", qualifiedByName = "subscriptionCount")
        @Mapping(source = "message", target = "message")
        @Mapping(source = "success", target = "success")
        TopicResponse toResponse(Topic topic, String message, boolean success);

        // Simple mapping without message and success
        @Mapping(source = "id", target = "id")
        @Mapping(source = "title", target = "title")
        @Mapping(source = "content", target = "content")
        @Mapping(source = "createdAt", target = "createdAt")
        @Mapping(source = "subscribers", target = "subscriberCount", qualifiedByName = "subscriptionCount")
        @Mapping(target = "message", constant = "")
        @Mapping(target = "success", constant = "true")
        TopicResponse toResponse(Topic topic);

        @Named("subscriptionCount")
        default Integer subscriptionCount(Set<User> subscribers) {
                return subscribers != null ? subscribers.size() : 0;
        }
}