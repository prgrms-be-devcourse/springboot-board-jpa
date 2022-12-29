package com.spring.board.springboard.post.repository;

import com.spring.board.springboard.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
