package com.prgrms.board.post.repository;

import com.prgrms.board.post.dto.PostResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {
    Slice<PostResponse> findPostPageable(String keyword, Pageable pageable);
}
