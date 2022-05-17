package com.blessing333.boardapi.repository;

import com.blessing333.boardapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post as p join fetch p.writer m where p.id = :id")
    Optional<Post> findPostByIdWithMember(@Param("id") Long id);
}
