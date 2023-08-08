package com.blackdog.springbootBoardJpa.domain.post.repository;

import com.blackdog.springbootBoardJpa.domain.post.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findPostsByUserId(Long userId, Pageable pageable);

    void deleteByIdAndUserId(Long id, Long userId);

}
