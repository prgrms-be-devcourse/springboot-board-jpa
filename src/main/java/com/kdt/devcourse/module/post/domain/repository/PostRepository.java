package com.kdt.devcourse.module.post.domain.repository;

import com.kdt.devcourse.module.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
