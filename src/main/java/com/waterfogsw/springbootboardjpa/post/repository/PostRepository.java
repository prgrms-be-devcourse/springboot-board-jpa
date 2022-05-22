package com.waterfogsw.springbootboardjpa.post.repository;

import com.waterfogsw.springbootboardjpa.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
