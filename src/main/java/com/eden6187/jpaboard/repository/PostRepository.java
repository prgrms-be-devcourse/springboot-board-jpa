package com.eden6187.jpaboard.repository;

import com.eden6187.jpaboard.model.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  @EntityGraph(attributePaths = "user", type = EntityGraphType.FETCH)
  Optional<Post> findById(Long id);
}
