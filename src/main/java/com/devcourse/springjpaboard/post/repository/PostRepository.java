package com.devcourse.springjpaboard.post.repository;

import com.devcourse.springjpaboard.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
