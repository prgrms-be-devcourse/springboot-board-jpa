package com.prgrms.springbootboardjpa.post.repository;

import com.prgrms.springbootboardjpa.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
