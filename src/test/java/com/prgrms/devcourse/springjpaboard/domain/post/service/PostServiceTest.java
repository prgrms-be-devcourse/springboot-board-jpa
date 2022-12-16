package com.prgrms.devcourse.springjpaboard.domain.post.service;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Mock
	PostRepository postRepository;

	@InjectMocks
	PostService postService;

	public User createUser() {
		return User.builder()
			.name("geonwoo")
			.age(25)
			.hobby("basketball")
			.build();
	}

	public Post createPost(User user) {
		return Post.builder()
			.user(user)
			.title("hello")
			.content("hi~")
			.build();
	}

	@Test
	@DisplayName("Post를 저장한다 - 성공")
	void createTest() {

		User user = createUser();

		Post post = createPost(user);

		Mockito.when(postRepository.save(post)).thenReturn(post);

		postService.create(post);

		Mockito.verify(postRepository).save(post);

	}

	@Test
	@DisplayName("Post를 id를 사용하여 조회한다 - 성공")
	void findByIdTest() {

		User user = createUser();

		Post post = createPost(user);

		Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		Post findPost = postService.findById(post.getId());

		Mockito.verify(postRepository).findById(post.getId());
		Assertions.assertThat(findPost).isEqualTo(post);

	}

	@Test
	@DisplayName("Post를 id를 사용하여 조회하고, Post 객체를 통해 변경감지로 업데이트한다.")
	void updateTest() {

		User user = createUser();

		Post post1 = createPost(user);
		Post post2 = createPost(user);

		Mockito.when(postRepository.findById(post1.getId())).thenReturn(Optional.of(post1));

		postService.update(post1.getId(), post2);

		Assertions.assertThat(post1)
			.hasFieldOrPropertyWithValue("title", post2.getTitle())
			.hasFieldOrPropertyWithValue("content", post2.getContent());

		Mockito.verify(postRepository).findById(post1.getId());

	}

	@Test
	@DisplayName("파라미터로 받은 cursorId보다 작은 값이 있다면 true, 없으면 false를 반환한다.")
	void hasNextTest() {

		Long cursorId = 2L;

		Mockito.when(postRepository.existsByIdLessThan(cursorId)).thenReturn(true);

		postService.hasNext(cursorId);

		Mockito.verify(postRepository).existsByIdLessThan(cursorId);

	}

	@Test
	@DisplayName("리스트 마지막 요소의 id를 반환한다 - 성공")
	void getLastIdOfListTest() {

		User user = createUser();

		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		List<Post> postList = List.of(post1, post2, post3);

		Long lastIdOfList = postService.getLastIdOfList(postList);

		Assertions.assertThat(lastIdOfList).isEqualTo(post3.getId());

	}

	@Test
	@DisplayName("파라미터로 받은 커서아이디 보다 낮은 커서 아이디의 데이터를 size 만큼 조회한다.")
	void findAllTest() {

		User user = createUser();

		Post post1 = createPost(user);
		Post post2 = createPost(user);

		Long cursorId = 3L;
		int size = 2;
		PageRequest pageRequest = PageRequest.of(0, size);

		List<Post> postList = List.of(post2, post1);

		Mockito.when(postRepository.findByIdLessThanOrderByIdDesc(cursorId, pageRequest))
			.thenReturn(postList);

		postService.findAll(cursorId, size);

		Mockito.verify(postRepository).findByIdLessThanOrderByIdDesc(cursorId, pageRequest);

	}
}