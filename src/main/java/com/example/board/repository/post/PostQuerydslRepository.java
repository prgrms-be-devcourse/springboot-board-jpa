package com.example.board.repository.post;

import com.example.board.domain.Post;
import com.example.board.dto.request.post.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQuerydslRepository {
    Page<Post> findAll(PostSearchCondition condition, Pageable pageable);
}
