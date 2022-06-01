package com.hyunji.jpaboard.domain.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "user")
    @Query(value = "select p from Post p", countQuery = "select count(p.id) from Post p")
    Page<Post> findPageWithUser(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    @Query("select p from Post p where p.id = :id")
    Optional<Post> findPostByIdWithUser(@Param("id") Long id);
}
