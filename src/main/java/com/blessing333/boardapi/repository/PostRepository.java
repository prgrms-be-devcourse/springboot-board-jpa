package com.blessing333.boardapi.repository;

import com.blessing333.boardapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
