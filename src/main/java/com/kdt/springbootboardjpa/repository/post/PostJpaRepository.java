package com.kdt.springbootboardjpa.repository.post;

import com.kdt.springbootboardjpa.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}