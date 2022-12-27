package com.prgrms.devcourse.springjpaboard.domain.post.application;

import static com.prgrms.devcourse.springjpaboard.domain.post.TestPostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.TestUserObjectProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
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
		assertThat(findPost).isEqualTo(post);
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
		assertThat(post1)
			.hasFieldOrPropertyWithValue("title", post2.getTitle())
			.hasFieldOrPropertyWithValue("content", post2.getContent());
		verify(postRepository).findById(post1.getId());

	}

	@Test
	@DisplayName("파라미터로 받은 cursorId 보다 작은 Post 데이터를 size 만큼 조회한다 - 성공")
	void findAllTest() {

		//given
		Long cursorId = 14L;
		Integer size = 3;
		User user = createUser();
		List<Post> postList = createPostList(user);

		Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());

		Slice<Post> postSlice = new SliceImpl<>(postList, pageable, true);

		when(postRepository.findByIdLessThan(cursorId, pageable)).thenReturn(postSlice);

		//when
		Slice<Post> slice = postService.findAll(cursorId, size);

		//then
		assertThat(slice).isEqualTo(postSlice);

		verify(postRepository).findByIdLessThan(cursorId, pageable);

	}

	@Test
	@DisplayName("파라미터로 받은 cursorId 보다 작은 Post 데이터를 size 만큼 조회한다 - 성공")
	void findAllSizeTest() {

		//given
		Long cursorId = 16L;
		Integer size = 8;
		User user = createUser();
		List<Post> postList = createPostList(user);

		Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());

		Slice<Post> postSlice = new SliceImpl<>(postList, pageable, true);

		when(postRepository.findByIdLessThan(cursorId, pageable)).thenReturn(postSlice);

		//when
		Slice<Post> slice = postService.findAll(cursorId, size);

		//then
		assertThat(slice).isEqualTo(postSlice);

		verify(postRepository).findByIdLessThan(cursorId, pageable);

	}
}