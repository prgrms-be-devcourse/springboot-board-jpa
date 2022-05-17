package com.programmers.epicblues.jpa_board.repository;

import com.programmers.epicblues.jpa_board.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long> {

}
