package com.example.boardjpa.repository;

import com.example.boardjpa.domain.Post;
import com.example.boardjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
