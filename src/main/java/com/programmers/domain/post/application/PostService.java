package com.programmers.domain.post.application;

import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    public Long createPost(PostDto postDto);

    public PostDto findPost(Long postId);

    public Page<PostDto> findAll(Pageable pageable);

    public PostDto updatePost(PostUpdateDto postUpdateDto, Long postId);
}
