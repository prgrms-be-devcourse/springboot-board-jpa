package com.prgrms.board.service;

import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.PostCreateDto;
import com.prgrms.board.dto.PostResponseDto;
import com.prgrms.board.dto.PostUpdateDto;
import org.springframework.data.domain.Pageable;

public interface PostService {
    Long register(PostCreateDto postCreateDto);

    PostResponseDto findById(Long postId);

    CursorResult findAll(Long cursorId, Pageable pageable);

    Long update(PostUpdateDto updateDto);
}
