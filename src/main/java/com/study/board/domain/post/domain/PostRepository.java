package com.study.board.domain.post.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.writer")
    List<Post> findAll();

    @Query("select p from Post p join fetch p.writer where p.id = :id")
    Optional<Post> findById(Long id);
}
