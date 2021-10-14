package com.example.jpaboard.repository;

import com.example.jpaboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
