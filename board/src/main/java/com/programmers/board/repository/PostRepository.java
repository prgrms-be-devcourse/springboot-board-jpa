package com.programmers.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.board.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
