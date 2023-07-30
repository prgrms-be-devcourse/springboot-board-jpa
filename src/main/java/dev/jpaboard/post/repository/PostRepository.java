package dev.jpaboard.post.repository;

import dev.jpaboard.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findPostByUserId(Long userId, Pageable pageable);

}
