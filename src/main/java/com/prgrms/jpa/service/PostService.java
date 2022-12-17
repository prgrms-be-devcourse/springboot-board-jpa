package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.post.PostsResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import org.springframework.data.domain.Pageable;

public interface PostService {

    long create(CreatePostRequest createPostRequest);

    PostsResponse findAll(Pageable pageable);

    PostResponse findById(long id);

    void update(long id, UpdatePostRequest updatePostRequest);
}
