package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.user where p.id = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);

    @Query(
            value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAllFetchJoinWithPaging(Pageable pageable);

}
