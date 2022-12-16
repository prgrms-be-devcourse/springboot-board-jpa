package com.prgrms.devcourse.springjpaboard.domain.post.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@BeforeAll
	void setup() {

		User user = User.builder()
			.name("geonwoo")
			.age(25)
			.hobby("basketball")
			.build();

		userRepository.save(user);

		for (int i = 0; i < 3; i++) {
			Post post = Post.builder()
				.user(user)
				.title("자바의 정석" + i)
				.content("자바 배우자" + i)
				.build();
			postRepository.save(post);
		}

	}

	@Test
	@DisplayName("Post를 id 내림차순으로 조회")
	void findAllByOrderByIdDescTest() {

		int pageSize = 3;

		PageRequest pageRequest = PageRequest.of(0, pageSize);

		List<Post> postList = postRepository.findAllByOrderByIdDesc(pageRequest);

		Assertions.assertThat(postList.get(0).getId()).isEqualTo(3);
		Assertions.assertThat(postList.get(1).getId()).isEqualTo(2);
		Assertions.assertThat(postList.get(2).getId()).isEqualTo(1);
	}

	@Test
	@DisplayName("파라미터의 id 보다 작은 id 값을 가진 Post를 Id 내림차순으로 조회")
	void findByIdLessThanOrderByIdDesc() {

		int pageSize = 2;
		Long cursorId = 3L;

		PageRequest pageRequest = PageRequest.of(0, pageSize);

		List<Post> postList = postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageRequest);

		System.out.println(postList.size());

		Assertions.assertThat(postList.get(0).getId()).isEqualTo(2);
		Assertions.assertThat(postList.get(1).getId()).isEqualTo(1);

	}

	@Test
	@DisplayName("파라미터의 id 보다 작은 id 값이 존재 유무를 참 거짓으로 반환")
	void existsByIdLessThanTest() {

		Long lastPostId = 1L;

		Boolean exists = postRepository.existsByIdLessThan(lastPostId);

		Assertions.assertThat(exists).isFalse();
	}

}