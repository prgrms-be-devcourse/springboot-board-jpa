package com.prgrms.boardapp.repository;

import com.prgrms.boardapp.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
