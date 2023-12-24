package com.programmers.springboard.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
