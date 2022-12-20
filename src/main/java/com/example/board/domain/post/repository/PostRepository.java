package com.example.board.domain.post.repository;

import com.example.board.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
 // page 조회
  //Page findAllOrderByCreatedAt(Pageable pageable);
}
