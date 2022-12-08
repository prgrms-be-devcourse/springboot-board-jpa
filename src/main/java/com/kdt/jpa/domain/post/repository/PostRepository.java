package com.kdt.jpa.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdt.jpa.domain.post.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
