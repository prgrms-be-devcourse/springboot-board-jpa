package com.programmers.jpa_board.post.infra;

import com.programmers.jpa_board.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
