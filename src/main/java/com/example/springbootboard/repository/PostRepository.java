package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
