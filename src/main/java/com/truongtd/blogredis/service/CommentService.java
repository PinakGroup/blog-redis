package com.truongtd.blogredis.service;

import com.truongtd.blogredis.entities.Comment;
import com.truongtd.blogredis.entities.NotificationEmail;
import com.truongtd.blogredis.entities.Post;
import com.truongtd.blogredis.entities.User;
import com.truongtd.blogredis.exceptions.PostNotFoundException;
import com.truongtd.blogredis.mapper.CommentMapper;
import com.truongtd.blogredis.model.CommentModel;
import com.truongtd.blogredis.repository.CommentRepository;
import com.truongtd.blogredis.repository.PostRepository;
import com.truongtd.blogredis.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentModel commentModel) {
        Post post = postRepository.findById(commentModel.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentModel.getPostId().toString()));
        Comment comment = commentMapper.map(commentModel, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentModel> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    public List<CommentModel> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
