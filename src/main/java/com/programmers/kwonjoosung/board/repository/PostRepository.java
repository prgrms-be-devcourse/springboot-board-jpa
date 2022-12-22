package com.programmers.kwonjoosung.board.repository;

import com.programmers.kwonjoosung.board.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUser_Id(Long id);

}