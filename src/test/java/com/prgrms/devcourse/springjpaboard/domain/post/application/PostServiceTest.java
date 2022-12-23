package com.prgrms.devcourse.springjpaboard.domain.post.application;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@Mock
	PostRepository postRepository;

	@InjectMocks
	PostService postService;

	@Test
	@DisplayName("Post를 저장한다 - 성공")
	void createTest() {

		//given
		User user = createUser();

		Post post = createPost(user);

		when(postRepository.save(post)).thenReturn(post);

		//when
		postService.create(post);

		//then
		verify(postRepository).save(post);

	}

	@Test
	@DisplayName("Post를 id를 사용하여 조회한다 - 성공")
	void findByIdTest() {

		//given
		User user = createUser();

		Post post = createPost(user);

		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		//when
		Post findPost = postService.findById(post.getId());

		//then
		Assertions.assertThat(findPost).isEqualTo(post);
		verify(postRepository).findById(post.getId());

	}

	@Test
	@DisplayName("Post를 id를 사용하여 조회하고, Post 객체를 사용하여 수정한다 - 성공")
	void updateTest() {

		//given
		User user = createUser();

		Post post1 = createPost("제목1", "내용1", user);
		Post post2 = createPost("제목2", "내용2", user);

		when(postRepository.findById(post1.getId())).thenReturn(Optional.of(post1));

		//when
		postService.update(post1.getId(), post2.getTitle(), post2.getContent());

		//then
		Assertions.assertThat(post1)
			.hasFieldOrPropertyWithValue("title", post2.getTitle())
			.hasFieldOrPropertyWithValue("content", post2.getContent());
		verify(postRepository).findById(post1.getId());

	}

	@Test
	@DisplayName("파라미터로 받은 cursorId 보다 w작은 Post 데이터를 size 만큼 조회한다 - 성공")
	void findAllTest() {

		Long cursorId = null;
		Integer size = 3;
		Pageable pageable = PageRequest.of(0, size + 1);
		User user = createUser();
		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		List<Post> postList = List.of(post3, post2, post1);
		Long nextCursorId = postList.get(postList.size() - 1).getId();

		boolean hasNext = false;

		when(postRepository.findAllByOrderByIdDesc(pageable)).thenReturn(postList);

		CursorResult cursorResult = postService.findAll(cursorId, size);

		Assertions.assertThat(cursorResult)
			.hasFieldOrPropertyWithValue("postList", postList)
			.hasFieldOrPropertyWithValue("nextCursorId", nextCursorId)
			.hasFieldOrPropertyWithValue("hasNext", hasNext);

		verify(postRepository).findAllByOrderByIdDesc(pageable);

	}
}