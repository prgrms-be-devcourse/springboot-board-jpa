package com.kdt.boardMission.repository;

import com.kdt.boardMission.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
