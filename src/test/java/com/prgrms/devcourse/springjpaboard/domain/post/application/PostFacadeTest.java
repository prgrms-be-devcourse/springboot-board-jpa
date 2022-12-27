package com.prgrms.devcourse.springjpaboard.domain.post.application;

import static com.prgrms.devcourse.springjpaboard.domain.post.TestPostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.TestUserObjectProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.UserService;

@ExtendWith(MockitoExtension.class)
class PostFacadeTest {

	@Mock
	PostConverter postConverter;

	@Mock
	PostService postService;

	@Mock
	UserService userService;

	@InjectMocks
	PostFacade postFacade;

	@Test
	@DisplayName("dto를 사용하여 user를 조회하고, dto를 post로 변환하여 저장한다.")
	void createTest() {

		//given
		Long userId = 1L;
		User user = createUser();
		Long postId = 1L;
		Post post = createPost(user);
		PostCreateRequest postCreateRequestDto = createPostCreateRequest(userId);
		PostCreateResponse postCreateResponseDto = createPostCreteResponse(postId);

		when(userService.findById(userId)).thenReturn(user);
		when(postConverter.toPost(postCreateRequestDto, user)).thenReturn(post);
		when(postService.create(post)).thenReturn(postId);
		when(postConverter.toPostCreateResponse(postId)).thenReturn(postCreateResponseDto);

		//when
		PostCreateResponse createResponseDto = postFacade.create(postCreateRequestDto);

		//then
		verify(userService).findById(userId);
		verify(postService).create(post);
		verify(postConverter).toPost(postCreateRequestDto, user);
		verify(postConverter).toPostCreateResponse(postId);
		assertThat(createResponseDto).hasFieldOrPropertyWithValue("id", postId);
	}

	@Test
	@DisplayName("id를 사용하여 수정할 post를 조회하고, dto의 내용으로 수정한다.")
	void updateTest() {

		//given
		Long postId = 1L;
		User user = createUser();
		PostUpdateRequest postUpdateDto = createPostUpdateRequest();

		doNothing().when(postService).update(postId, postUpdateDto.getTitle(), postUpdateDto.getContent());

		//when
		postFacade.update(postId, postUpdateDto);

		//then
		verify(postService).update(postId, postUpdateDto.getTitle(), postUpdateDto.getContent());

	}

	@Test
	@DisplayName("id를 사용하여 Post를 조회하고 dto로 반환한다.")
	void findById() {

		//given
		Long postId = 1L;
		User user = createUser();
		Post post = createPost(user);
		PostSearchResponse postSearchResponse = createPostSearchResponse(postId);

		when(postService.findById(postId)).thenReturn(post);
		when(postConverter.toPostResponse(post)).thenReturn(postSearchResponse);

		//when
		PostSearchResponse findPost = postFacade.findById(postId);

		//then
		verify(postService).findById(postId);
		verify(postConverter).toPostResponse(post);

		assertThat(findPost)
			.hasFieldOrPropertyWithValue("id", postId)
			.hasFieldOrPropertyWithValue("title", postSearchResponse.getTitle())
			.hasFieldOrPropertyWithValue("content", postSearchResponse.getContent());
	}

	@Test
	@DisplayName("커서기반 페이지네이션으로 post를 조회하고, 조회한 post와 다음 페이지 존재 여부를 반환한다.")
	void findAllTest() {

		//given
		Long cursorId = null;
		int size = 3;
		User user = createUser();

		List<Post> postList = createPostList(user);

		Slice<Post> postSlice = new SliceImpl<>(postList);

		when(postService.findAll(cursorId, size)).thenReturn(postSlice);

		//when
		PostSearchResponses postSearchResponses = postFacade.findAll(cursorId, size);

		//then
		verify(postService).findAll(cursorId, size);

		Assertions.assertThat(postSearchResponses)
			.hasFieldOrPropertyWithValue("posts", postSlice.getContent())
			.hasFieldOrPropertyWithValue("hasNext", postSlice.hasNext());

	}

}