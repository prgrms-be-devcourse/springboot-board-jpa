package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByUser(User user, Pageable pageable);
}
