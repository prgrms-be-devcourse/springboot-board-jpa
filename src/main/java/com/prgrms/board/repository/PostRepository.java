package com.prgrms.board.repository;

import com.prgrms.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.user pu WHERE pu.userId = :userId",
            countQuery = "SELECT count(p) FROM Post p")
    Page<Post> findByUser(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT p FROM Post p JOIN FETCH p.user",
            countQuery = "SELECT count(p) FROM Post p")
    Page<Post> findAllWithUser(Pageable pageable);
}