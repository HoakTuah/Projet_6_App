package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.common.CommentDto;
import com.openclassrooms.mddapi.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * Mapper interface for Comment entity and related DTOs.
 * Provides methods to convert between Comment entities and DTOs used in the
 * application.
 * Uses MapStruct for automatic implementation generation.
 *
 * @author Herry Khoalinh
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Converts a Comment entity to a CommentDto.
     * Maps all relevant fields including the associated post and user information.
     *
     * @param comment The comment entity to convert
     * @return A CommentDto containing the comment's information
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "post.id", target = "postId"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.usernameDisplay", target = "username"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "commentedAt", target = "commentedAt")
    })
    CommentDto toDto(Comment comment);

    /**
     * Converts a list of Comment entities to a list of CommentDtos.
     * Uses the toDto method for each individual comment conversion.
     *
     * @param comments The list of comment entities to convert
     * @return A list of CommentDtos containing the comments' information
     */
    List<CommentDto> toDtoList(List<Comment> comments);
}