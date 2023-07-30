package com.jpaboard.domain.post.infrastructure;

import com.jpaboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByTitleContaining(String title, Pageable pageable);

    Page<Post> findAllByContentContaining(String content, Pageable pageable);

    Page<Post> findAllByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
