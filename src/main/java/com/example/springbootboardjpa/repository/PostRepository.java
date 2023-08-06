package com.example.springbootboardjpa.repository;

import com.example.springbootboardjpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
