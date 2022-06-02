package com.prgrms.boardjpa.application.post.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.boardjpa.application.post.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBy(Pageable pageable);

	List<Post> findAllByCreatedBy(String createdBy, Pageable pageable);
}
