package com.prgrms.jpaboard.domain.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p join p.user")
    Page<Post> findAllWithUser(Pageable pageable);

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long Id);
}
