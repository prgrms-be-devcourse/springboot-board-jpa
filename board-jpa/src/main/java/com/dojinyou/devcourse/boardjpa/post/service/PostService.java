package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.post.controller.dto.PostUpdateRequest;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostUpdateDto;

public interface PostService {
    void create(long userId, PostCreateDto postCreateDto);

    PostResponseDto findById(long id);

    void updateById(long id, PostUpdateDto postUpdateDto);
}
