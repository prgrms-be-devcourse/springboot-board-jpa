package com.example.board.domain.entity.repository;

import com.example.board.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 유저와 함께 조회(fetch Join)
    @Query("select p from Post p join fetch p.user where p.id = :postId")
    Optional<Post> findByIdWithUser(@Param("postId") Long postId);

    // 댓글과 함께 조회
    @Query("select p from Post p join fetch p.user left join fetch p.comments where p.id = :postId")
    Optional<Post> findByIdWithUserAndComments(@Param("postId") Long postId);

    // 페이징된 게시글만 조회
    @Query(value = "select p from Post p join fetch p.user u",
            countQuery = "select count(p) from Post p")
    Page<Post> findAll(Pageable pageable);
}
