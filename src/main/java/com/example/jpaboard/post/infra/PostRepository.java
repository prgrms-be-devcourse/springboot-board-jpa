package com.example.jpaboard.post.infra;

import com.example.jpaboard.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
