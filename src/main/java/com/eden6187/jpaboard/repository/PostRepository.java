package com.eden6187.jpaboard.repository;

import com.eden6187.jpaboard.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
