package com.kdt.jpa.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdt.jpa.domain.post.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
