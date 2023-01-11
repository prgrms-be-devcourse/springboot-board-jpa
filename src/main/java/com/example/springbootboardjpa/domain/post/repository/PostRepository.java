package com.example.springbootboardjpa.domain.post.repository;

import com.example.springbootboardjpa.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
