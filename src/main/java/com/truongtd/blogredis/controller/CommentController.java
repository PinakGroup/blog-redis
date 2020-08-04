package com.truongtd.blogredis.controller;

import com.truongtd.blogredis.model.CommentModel;
import com.truongtd.blogredis.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.security.auth.callback.ConfirmationCallback.OK;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/comments/")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentModel commentModel) {
        commentService.save(commentModel);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentModel>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentModel>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
}
