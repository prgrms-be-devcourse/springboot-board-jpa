package com.pppp0722.boardjpa.post.service;

import com.pppp0722.boardjpa.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long save(PostDto postDto);

    PostDto findById(Long id);

    Page<PostDto> findAll(Pageable pageable);

    PostDto update(PostDto postDto);
}
