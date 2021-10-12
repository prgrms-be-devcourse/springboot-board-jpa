package com.example.board.repository;

import com.example.board.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@BeforeEach
	void setup() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("게시글 저장이 정상적으로 이루어지는가")
	void addPost() {
		// given
		Post post = new Post();
		post.setTitle("Test Post 1");
		post.setContent("Test Post Content 1");
		post.setCreatedAt(LocalDateTime.now());
		post.setCreatedBy("Test User 1");

		// when
		postRepository.save(post);

		// then
		Post foundPost = postRepository.findById(post.getId()).orElseThrow(
				() -> new RuntimeException("No such post found")
		);
		assertThat(foundPost).isEqualTo(post);
	}


}