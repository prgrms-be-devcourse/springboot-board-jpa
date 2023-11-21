package com.example.board.domain.post.repository;

import com.example.board.domain.post.dto.PostPageCondition;
import com.example.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;

public interface CustomPostRepository {

    Page<Post> findPostsByCondition(PostPageCondition condition);
}
