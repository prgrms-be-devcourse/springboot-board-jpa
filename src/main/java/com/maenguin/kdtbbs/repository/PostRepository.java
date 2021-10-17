package com.maenguin.kdtbbs.repository;

import com.maenguin.kdtbbs.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
