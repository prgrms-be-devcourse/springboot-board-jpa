package com.programmers.board.core.post.domain.repository;

import com.programmers.board.core.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
