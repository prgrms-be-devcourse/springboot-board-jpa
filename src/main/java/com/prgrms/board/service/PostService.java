package com.prgrms.board.service;

import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.dto.response.PostResponseDto;
import com.prgrms.board.dto.request.PostUpdateDto;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long register(PostCreateDto postCreateDto);

    PostResponseDto findById(Long postId);

    CursorResult findAll(Long cursorId, Pageable pageable);

    Long update(Long postId, PostUpdateDto updateDto);
}
