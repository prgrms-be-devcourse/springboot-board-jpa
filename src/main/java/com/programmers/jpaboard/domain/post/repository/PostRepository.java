package com.programmers.jpaboard.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.jpaboard.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
