package com.ray.springbootboard.controller.dto;

import com.ray.springbootboard.domain.Post;
import com.ray.springbootboard.domain.User;

public record PostSaveRequest(String title, String content) {
    public Post toEntity(Long userId) {
        return new Post(title, content, User.withOnlyId(userId));
    }
}
