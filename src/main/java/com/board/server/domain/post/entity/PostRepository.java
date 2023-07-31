package com.board.server.domain.post.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // CREATE

    // READ
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("select p from Post p join fetch p.user where p.postId = :postId")
    Optional<Post> findById(Long postId);

    // UPDATE

    // DELETE

}
