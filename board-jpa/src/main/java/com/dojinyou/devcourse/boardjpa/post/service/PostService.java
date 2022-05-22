package com.dojinyou.devcourse.boardjpa.post.service;

import com.dojinyou.devcourse.boardjpa.post.service.dto.PostCreateDto;

public interface PostService {
    void create(long userId, PostCreateDto postCreateDto);
}
