package com.truongtd.blogredis.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String messages) {
        super(messages);
    }
}
