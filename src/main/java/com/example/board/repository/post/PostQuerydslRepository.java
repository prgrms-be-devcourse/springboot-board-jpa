package com.example.board.repository.post;

import com.example.board.domain.Post;
import com.example.board.dto.request.post.PostSearchCondition;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostQuerydslRepository {
    Long countAll(PostSearchCondition condition);

    List<Post> findAll(PostSearchCondition condition, Pageable pageable);
}
