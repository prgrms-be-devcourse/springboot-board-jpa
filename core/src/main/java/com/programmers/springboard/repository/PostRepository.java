package com.programmers.springboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.programmers.springboard.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
