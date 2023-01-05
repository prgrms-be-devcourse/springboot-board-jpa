package com.example.board.domain.post.repository;

import com.example.board.domain.post.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Optional<Post> findByIdAndMemberId(Long postId, Long memberId);
}
