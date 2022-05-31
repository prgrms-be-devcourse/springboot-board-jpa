package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void create(long userId, PostCreateDto postCreateDto);

    PostResponseDto findById(long id);

    void updateById(long id, PostUpdateDto postUpdateDto);

    List<PostResponseDto> findAll(Pageable pageable);
}
