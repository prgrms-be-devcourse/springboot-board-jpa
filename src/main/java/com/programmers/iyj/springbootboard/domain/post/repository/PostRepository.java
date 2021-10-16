package com.programmers.iyj.springbootboard.domain.post.repository;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
