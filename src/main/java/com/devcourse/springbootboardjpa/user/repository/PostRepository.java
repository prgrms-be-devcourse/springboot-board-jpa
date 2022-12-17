package com.devcourse.springbootboardjpa.user.repository;

import com.devcourse.springbootboardjpa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
