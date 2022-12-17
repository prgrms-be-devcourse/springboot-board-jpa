package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.post.*;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostIdResponse create(CreatePostRequest createPostRequest);

    PostsResponse findAll(Pageable pageable);

    PostResponse findById(long id);

    void update(long id, UpdatePostRequest updatePostRequest);
}
