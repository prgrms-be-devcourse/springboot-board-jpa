package com.prgrms.devcourse.springjpaboard.domain.post.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

	Slice<Post> findByIdLessThan(@Param("id") Long id, Pageable pageable);
}
