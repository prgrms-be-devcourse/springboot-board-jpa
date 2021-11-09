package com.example.boardbackend.repository;

import com.example.boardbackend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post AS p WHERE p.user.id = :createdBy")
    List<Post> findByCreatedBy(Long createdBy);

    Page<Post> findByTitleContains(String title, Pageable pageable);
    Page<Post> findByContentContains(String content, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.user u WHERE u.name like %:name%")
    Page<Post> findByUserNameContains(String name, Pageable pageable);
}
