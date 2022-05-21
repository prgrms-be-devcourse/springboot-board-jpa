package com.pppp0722.boardjpa.service.post;

import com.pppp0722.boardjpa.web.dto.PostRequestDto;
import com.pppp0722.boardjpa.web.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostResponseDto save(PostRequestDto postRequestDto);

    PostResponseDto findById(Long id);

    Page<PostResponseDto> findAll(Pageable pageable);

    Page<PostResponseDto> findByUserId(Long id, Pageable pageable);

    PostResponseDto update(Long id, PostRequestDto postRequestDto);

    void deleteById(Long id);
}
