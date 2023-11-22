package com.example.board.repository.post;

import com.example.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, PostQuerydslRepository {

    @Query("select p from Post p join fetch p.author a where p.id = :id")
    Optional<Post> findByIdWithAuthor(@Param("id") Long id);
}
