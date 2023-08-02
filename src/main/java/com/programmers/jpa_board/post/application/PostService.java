package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse create(CreatePostRequest request);

    PostResponse findById(Long postId);

    Page<PostResponse> findAll(Pageable pageable);
}
