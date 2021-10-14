package com.example.springbootboard.repository;

import com.example.springbootboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedByOrderByCreatedAt(String name);

    @Query("select p from Post as p where p.title like %?1% or p.content like %?1%")
    List<Post> findAllByKeyword(String keyword);
}
