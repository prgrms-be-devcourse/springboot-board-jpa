package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;

import java.util.List;

public interface PostService {
    long create(CreatePostRequest createPostRequest);

    List<Post> findAll();

    PostResponse findById(long id);

    void update(long id, UpdatePostRequest updatePostRequest);
}
