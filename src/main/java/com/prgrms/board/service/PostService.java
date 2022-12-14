package com.prgrms.board.service;

import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;

public interface PostService {
    Long register(PostCreateDto postCreateDto);

    PostResponseDto findById(Long postId);
}
