package com.study.board.domain.post.repository;

import com.study.board.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "writer")
    Page<Post> findAll(Pageable pageable);

    @Query("select p from Post p join fetch p.writer where p.id = :id")
    Optional<Post> findById(Long id);
}
