package com.prgrms.boardjpa.application.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.boardjpa.application.post.model.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
}
