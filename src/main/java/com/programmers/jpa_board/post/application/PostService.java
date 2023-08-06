package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.request.UpdatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse save(CreatePostRequest request);

    PostResponse getOne(Long postId);

    Page<PostResponse> getPage(Pageable pageable);

    PostResponse update(Long postId, UpdatePostRequest request);
}
