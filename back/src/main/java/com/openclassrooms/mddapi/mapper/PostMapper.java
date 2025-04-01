package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.common.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * Mapper interface for Post entity and related DTOs.
 * Provides methods to convert between Post entities and DTOs used in the
 * application.
 * Uses MapStruct for automatic implementation generation.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    /**
     * Converts a Post entity to a PostDto.
     * Maps all relevant fields including the associated user and topic information.
     * Includes:
     * <ul>
     * <li>Basic post information (id, title, content)</li>
     * <li>Author information (userId, username)</li>
     * <li>Topic information (topicId, topicTitle)</li>
     * <li>Publication timestamp</li>
     * </ul>
     *
     * @param post The post entity to convert
     * @return A PostDto containing the post's information
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.usernameDisplay", target = "username"),
            @Mapping(source = "topic.id", target = "topicId"),
            @Mapping(source = "topic.title", target = "topicTitle"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "publishedAt", target = "publishedAt")
    })
    PostDto toDto(Post post);
}