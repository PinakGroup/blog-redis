package com.truongtd.blogredis.repository;

import com.truongtd.blogredis.entities.Post;
import com.truongtd.blogredis.entities.User;
import com.truongtd.blogredis.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);
}
