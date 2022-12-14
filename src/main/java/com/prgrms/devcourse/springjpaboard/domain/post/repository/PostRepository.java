package com.prgrms.devcourse.springjpaboard.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
