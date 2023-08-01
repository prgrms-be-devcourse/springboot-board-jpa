package com.programmers.jpaboard.repository;

import com.programmers.jpaboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
