package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;

public interface PostService {
    void create(long userId, PostCreateDto postCreateDto);

    PostResponseDto findById(long id);
}
