package com.truongtd.blogredis.repository;

import com.truongtd.blogredis.entities.Post;
import com.truongtd.blogredis.entities.Subreddit;
import com.truongtd.blogredis.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);
    List<Post> findByUser(User user);
}
