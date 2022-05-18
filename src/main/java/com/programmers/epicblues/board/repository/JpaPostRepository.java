package com.programmers.epicblues.board.repository;

import com.programmers.epicblues.board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

}
