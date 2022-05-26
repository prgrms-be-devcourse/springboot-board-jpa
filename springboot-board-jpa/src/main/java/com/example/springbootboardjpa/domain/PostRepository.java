package com.example.springbootboardjpa.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByTitle(String title, Pageable pageable);

    @Query("SELECT o FROM Post AS o WHERE o.content LIKE %?1%")
    Page<Post> findAllByContent(String content, Pageable pageable);
}
