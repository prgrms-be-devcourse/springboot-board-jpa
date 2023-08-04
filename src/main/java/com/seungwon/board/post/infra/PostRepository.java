package com.seungwon.board.post.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.seungwon.board.post.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
