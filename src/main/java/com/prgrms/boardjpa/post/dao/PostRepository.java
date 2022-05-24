package com.prgrms.boardjpa.post.dao;

import com.prgrms.boardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
