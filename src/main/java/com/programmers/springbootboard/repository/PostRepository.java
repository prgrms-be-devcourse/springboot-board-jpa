package com.programmers.springbootboard.repository;

import com.programmers.springbootboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p left join p.user u where p.id = :id")
    Optional<Post> findPostWithUserById(@Param("id")Long id);

    @Query("select p from Post p left join p.user u")
    List<Post> findPostWithUserAll();
}
