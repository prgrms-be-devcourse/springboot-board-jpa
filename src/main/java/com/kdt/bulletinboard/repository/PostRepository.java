package com.kdt.bulletinboard.repository;

import com.kdt.bulletinboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
