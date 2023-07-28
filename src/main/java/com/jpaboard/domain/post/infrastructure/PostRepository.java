package com.jpaboard.domain.post.infrastructure;

import com.jpaboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByTitleOrderByCreateAt(String title, Pageable pageable);

    Page<Post> findAllByContentContainingOrderByCreateAt(String content, Pageable pageable);

    Page<Post> findAllByTitleContainingOrContentContainingOrderByCreateAt(String keyword, Pageable pageable);
}
