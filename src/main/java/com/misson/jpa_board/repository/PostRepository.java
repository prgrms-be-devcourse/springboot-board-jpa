package com.misson.jpa_board.repository;

import com.misson.jpa_board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
