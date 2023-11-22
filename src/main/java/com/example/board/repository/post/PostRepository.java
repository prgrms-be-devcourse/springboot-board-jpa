package com.example.board.repository.post;

import com.example.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydslRepository {
}
