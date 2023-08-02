package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;

public interface PostService {
    PostResponse create(CreatePostRequest request);
}
