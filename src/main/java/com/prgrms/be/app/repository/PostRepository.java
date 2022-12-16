package com.prgrms.be.app.repository;

import com.prgrms.be.app.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
