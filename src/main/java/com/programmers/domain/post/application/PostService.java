package com.programmers.domain.post.application;

import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    Long createPost(PostDto postDto);

    PostDto findPost(Long postId);

    List<PostDto> findAll(Pageable pageable);

    PostDto updatePost(PostUpdateDto postUpdateDto, Long postId);
}
