package com.programmers.jpa_board.post.application;

import com.programmers.jpa_board.post.domain.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostDto.CommonResponse save(PostDto.CreatePostRequest request);

    PostDto.CommonResponse getOne(Long postId);

    Page<PostDto.CommonResponse> getPage(Pageable pageable);

    PostDto.CommonResponse update(Long postId, PostDto.UpdatePostRequest request);
}
