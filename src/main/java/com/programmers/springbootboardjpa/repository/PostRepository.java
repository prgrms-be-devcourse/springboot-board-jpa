package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
