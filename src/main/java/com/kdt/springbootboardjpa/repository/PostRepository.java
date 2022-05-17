package com.kdt.springbootboardjpa.repository;

import com.kdt.springbootboardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
