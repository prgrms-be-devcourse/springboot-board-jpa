package com.programmers.kwonjoosung.board.repository;

import com.programmers.kwonjoosung.board.model.Post;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<PostInfo> findByUserId(Long id);

}