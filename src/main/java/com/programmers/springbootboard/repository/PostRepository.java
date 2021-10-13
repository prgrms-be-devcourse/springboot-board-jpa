package com.programmers.springbootboard.repository;

import com.programmers.springbootboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"user"})
    Optional<Post> findWithUserById(Long id);

    @Query(
            value = "select p from Post p left join fetch p.user",
            countQuery = "select count(p) from Post p"
    )
    Page<Post> findAllWithTeam(Pageable pageable);
}
