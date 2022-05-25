package com.prgrms.boardjpa.post;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBy(Pageable pageable);

	List<Post> findAllByCreatedBy(String createdBy, Pageable pageable);
}
