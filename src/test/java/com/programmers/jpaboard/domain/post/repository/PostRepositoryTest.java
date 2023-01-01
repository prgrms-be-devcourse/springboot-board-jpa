package com.programmers.jpaboard.domain.post.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.domain.user.repository.UserRepository;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("정상적인 게시글 저장과 조회를 성공한다.")
	void saveAndFindByIdPost() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");
		Post post = new Post("테스트", "테스트입니다.", user);

		// when
		userRepository.save(user);
		Post savedPost = postRepository.save(post);
		Post findPost = postRepository.findById(savedPost.getId()).get();

		// then
		assertThat(savedPost)
			.hasFieldOrPropertyWithValue("title", post.getTitle())
			.hasFieldOrPropertyWithValue("content", post.getContent());
		assertThat(findPost).isEqualTo(savedPost);
	}

	@Test
	@DisplayName("게시글의 수정을 성공한다.")
	void updatePost() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");
		Post post = new Post("테스트", "테스트입니다.", user);
		userRepository.save(user);
		String changeTitle = "변경된 테스트";
		String changeContent = "변경된 테스트입니다";

		// when
		post.changePost(changeTitle, changeContent);
		Post findPost = postRepository.findById(post.getId()).get();

		// then
		assertThat(findPost).isEqualTo(post);
	}

	@Test
	@DisplayName("게시글의 아이디로 삭제를 성공한다.")
	void deleteByIdPost() {
		// given
		User user = new User("권성준", "google@gmail.com", 100, "인터넷 서핑");
		Post post = new Post("테스트", "테스트입니다.", user);
		userRepository.save(user);

		// when
		postRepository.deleteById(post.getId());
		Optional<Post> optionalPost = postRepository.findById(post.getId());

		// then
		assertThat(optionalPost.isEmpty()).isTrue();
	}
}