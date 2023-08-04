package com.example.board.domain.post.repository;

import com.example.board.domain.post.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("select p from Post p join fetch p.user where p.id = :postId")
  Optional<Post> findById(@Param("postId") Long postId);
}