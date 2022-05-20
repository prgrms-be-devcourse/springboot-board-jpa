package org.programmers.springbootboardjpa.repository.user;

import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<Post> findFetchByPostId(Long postId);

    @EntityGraph(attributePaths = "user")
    Page<Post> findFetchAllBy(Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Page<Post> findFetchByUser(User user, Pageable pageable);
}