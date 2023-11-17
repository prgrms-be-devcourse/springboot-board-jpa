package com.example.board.repository;

import com.example.board.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = "user")
    @Query("select p from Post p ")
    Page<Post> findAllByEntityGraph(Pageable pageable);
}
