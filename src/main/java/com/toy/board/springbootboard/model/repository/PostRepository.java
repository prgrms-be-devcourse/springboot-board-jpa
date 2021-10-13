package com.toy.board.springbootboard.model.repository;

import com.toy.board.springbootboard.model.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
