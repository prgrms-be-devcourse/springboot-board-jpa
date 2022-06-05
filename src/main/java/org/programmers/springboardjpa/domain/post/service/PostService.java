package org.programmers.springboardjpa.domain.post.service;

import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostCreateRequest;
import org.programmers.springboardjpa.domain.post.dto.PostRequest.PostUpdateRequest;
import org.programmers.springboardjpa.domain.post.dto.PostResponse.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    PostResponseDto savePost(PostCreateRequest createRequest);

    PostResponseDto getPost(Long id);

    List<PostResponseDto> getPostList(Pageable pageable);

    PostResponseDto updatePost(Long id, PostUpdateRequest updateRequest);
}
