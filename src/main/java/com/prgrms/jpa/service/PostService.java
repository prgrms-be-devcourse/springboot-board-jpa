package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.CreatePostRequest;
import com.prgrms.jpa.controller.dto.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;

import java.util.List;

public interface PostService {
    long create(CreatePostRequest createPostRequest);

    List<Post> findAll();

    Post findById(long id);

    void update(long id, UpdatePostRequest updatePostRequest);
}
