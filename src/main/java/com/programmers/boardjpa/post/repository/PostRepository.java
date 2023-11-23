package com.programmers.boardjpa.post.repository;

import com.programmers.boardjpa.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p join fetch p.user WHERE p.id = :id")
    Optional<Post> findById(Long id);

    @Query("SELECT p FROM Post p join fetch p.user")
    Page<Post> findAllWithUser(Pageable pageable);
}
