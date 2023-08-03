package com.ray.springbootboard.controller.dto;

import com.ray.springbootboard.domain.Post;

public record PostSaveRequest(String title, String content) {
    public Post toEntity() {
         return new Post(null, title, content, null, null);
    }
}
