package org.programmers.springboardjpa.domain.post.service;

import org.programmers.springboardjpa.domain.post.dto.PostResponse;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequestDto;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostUpdateRequestDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostResponse.PostResponseDto savePost(PostCreateRequestDto createRequest);

    PostResponse.PostResponseDto getPost(Long id);

    List<PostResponse.PostResponseDto> getPostList(Pageable pageable);

    PostResponse.PostResponseDto updatePost(Long id, PostUpdateRequestDto updateRequest);
}
