package org.programmers.springbootboardjpa.repository.user;

import org.programmers.springbootboardjpa.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}