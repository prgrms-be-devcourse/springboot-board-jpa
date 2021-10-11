package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
