package com.devcourse.springbootboardjpahi.repository;

import com.devcourse.springbootboardjpahi.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
