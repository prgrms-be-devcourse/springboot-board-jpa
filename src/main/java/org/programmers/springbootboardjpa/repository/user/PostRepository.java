package org.programmers.springbootboardjpa.repository.user;

import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "user")
    @Query(countQuery = "SELECT COUNT(u) FROM User u")
    Optional<Post> findFetchByPostId(Long postId);

    @EntityGraph(attributePaths = "user")
    @Query(countQuery = "SELECT COUNT(u) FROM User u")
    Page<Post> findFetchAllBy(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    @Query(countQuery = "SELECT COUNT(u) FROM User u")
    Page<Post> findFetchByUser(User user, Pageable pageable);
}