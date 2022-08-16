package com.prgrms.boardjpa.application.post.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.user.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findAllBy(Pageable pageable);

	List<Post> findAllByCreatedBy(String createdBy, Pageable pageable);

	@Query("select pl.post from PostLike pl WHERE pl.user = :id")
	List<Post> findAllLikedBy(@Param("id") User user);
}
