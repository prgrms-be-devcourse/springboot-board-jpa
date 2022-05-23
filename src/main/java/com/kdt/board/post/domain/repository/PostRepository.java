package com.kdt.board.post.domain.repository;

import com.kdt.board.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user")
    List<Post> findAllOrderByCreatedAtDesc(Pageable pageable);

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findById(@Param("id") Long id);
}
