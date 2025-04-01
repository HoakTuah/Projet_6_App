package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.response.TopicResponse;
import com.openclassrooms.mddapi.entity.Topic;
import com.openclassrooms.mddapi.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import java.util.Set;

/**
 * Mapper interface for Topic entity and related DTOs.
 * Provides methods to convert between Topic entities and response DTOs used in
 * the application.
 * Uses MapStruct for automatic implementation generation.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface TopicMapper {

        /**
         * Converts a Topic entity to a TopicResponse with operation status.
         * Maps all relevant fields including subscriber count and operation status.
         *
         * @param topic   The topic entity to convert
         * @param message The operation message
         * @param success The operation success status
         * @return A TopicResponse containing the topic's information and operation
         *         status
         */
        @Mapping(source = "topic.id", target = "id")
        @Mapping(source = "topic.title", target = "title")
        @Mapping(source = "topic.content", target = "content")
        @Mapping(source = "topic.createdAt", target = "createdAt")
        @Mapping(source = "topic.subscribers", target = "subscriberCount", qualifiedByName = "subscriptionCount")
        @Mapping(source = "message", target = "message")
        @Mapping(source = "success", target = "success")
        TopicResponse toResponse(Topic topic, String message, boolean success);

        /**
         * Converts a Topic entity to a TopicResponse without operation status.
         * This is a simplified version that sets default values for message and
         * success.
         *
         * @param topic The topic entity to convert
         * @return A TopicResponse containing the topic's information with default
         *         status
         */
        @Mapping(source = "id", target = "id")
        @Mapping(source = "title", target = "title")
        @Mapping(source = "content", target = "content")
        @Mapping(source = "createdAt", target = "createdAt")
        @Mapping(source = "subscribers", target = "subscriberCount", qualifiedByName = "subscriptionCount")
        @Mapping(target = "message", constant = "")
        @Mapping(target = "success", constant = "true")
        TopicResponse toResponse(Topic topic);

        /**
         * Calculates the number of subscribers for a topic.
         * This is a named method used by MapStruct for custom mapping.
         *
         * @param subscribers The set of subscribers to count
         * @return The number of subscribers, or 0 if the set is null
         */
        @Named("subscriptionCount")
        default Integer subscriptionCount(Set<User> subscribers) {
                return subscribers != null ? subscribers.size() : 0;
        }
}