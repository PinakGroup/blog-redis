package com.truongtd.blogredis.repository;

import com.truongtd.blogredis.entities.Comment;
import com.truongtd.blogredis.entities.Post;
import com.truongtd.blogredis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUser(User user);

    List<Comment> findByPost(Post post);
}
