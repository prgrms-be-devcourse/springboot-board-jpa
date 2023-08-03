package com.prgrms.board.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.board.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
