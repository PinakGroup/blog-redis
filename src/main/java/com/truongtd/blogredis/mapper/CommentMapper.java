package com.truongtd.blogredis.mapper;

import com.truongtd.blogredis.entities.Comment;
import com.truongtd.blogredis.entities.Post;
import com.truongtd.blogredis.entities.User;
import com.truongtd.blogredis.model.CommentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentModel.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentModel commentModel, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentModel mapToDto(Comment comment);
}
