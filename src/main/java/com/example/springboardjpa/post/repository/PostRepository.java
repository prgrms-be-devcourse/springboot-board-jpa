package com.example.springboardjpa.post.repository;

import com.example.springboardjpa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
