package com.blessing333.boardapi.repository;

import com.blessing333.boardapi.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "writer")
    Optional<Post> findPostWithMemberById(Long id);

    @Override
    @EntityGraph(attributePaths = "writer")
    Page<Post> findAll(Pageable pageable);

}
