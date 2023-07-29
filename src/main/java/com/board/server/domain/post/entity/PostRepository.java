package com.board.server.domain.post.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // CREATE

    // READ
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // UPDATE

    // DELETE

}
