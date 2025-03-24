package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.common.CommentDto;
import com.openclassrooms.mddapi.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "post.id", target = "postId"),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "content", target = "content"),
            @Mapping(source = "commentedAt", target = "commentedAt")
    })
    CommentDto toDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);
}