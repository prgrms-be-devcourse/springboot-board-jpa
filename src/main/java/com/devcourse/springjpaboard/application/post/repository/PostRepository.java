package com.devcourse.springjpaboard.application.post.repository;

import com.devcourse.springjpaboard.application.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
