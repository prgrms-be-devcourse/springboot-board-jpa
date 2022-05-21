package com.pppp0722.boardjpa.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p from Post p where p.user.id = :id")
    Page<Post> findByUserId(Long id, Pageable pageable);
}
