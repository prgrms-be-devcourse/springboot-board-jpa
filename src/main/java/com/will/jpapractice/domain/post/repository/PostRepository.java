package com.will.jpapractice.domain.post.repository;

import com.will.jpapractice.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
