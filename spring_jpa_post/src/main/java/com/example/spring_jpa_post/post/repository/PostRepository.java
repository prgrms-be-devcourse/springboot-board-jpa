package com.example.spring_jpa_post.post.repository;

import com.example.spring_jpa_post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
