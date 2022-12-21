package com.prgms.springbootboardjpa.repository;

import com.prgms.springbootboardjpa.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
