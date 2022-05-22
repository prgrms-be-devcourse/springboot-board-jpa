package com.kdt.board.post.repository;

import com.kdt.board.post.domain.Post;
import com.kdt.board.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findPostsByUserId(Long userId, Pageable pageable);
}
