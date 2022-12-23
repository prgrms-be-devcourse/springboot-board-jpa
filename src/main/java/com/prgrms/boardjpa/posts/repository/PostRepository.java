package com.prgrms.boardjpa.posts.repository;

import com.prgrms.boardjpa.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query(value = "SELECT p FROM Post p JOIN fetch p.user u",
      countQuery = "SELECT COUNT(p) FROM Post p")
  Page<Post> findAll(Pageable pageable);

  @Query("SELECT p FROM Post p JOIN fetch p.user where p.id = :postId")
  Optional<Post> findById(Long postId);
}
