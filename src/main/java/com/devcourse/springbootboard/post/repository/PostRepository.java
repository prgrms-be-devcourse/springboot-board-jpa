package com.devcourse.springbootboard.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devcourse.springbootboard.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
