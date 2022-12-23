package com.prgrms.devcourse.springjpaboard.domain.post.application.facade;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostService;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
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
	@DisplayName("Dto를 사용하여 user를 조회하고, Dto를 Post로 변환하여 저장한다.")
	void createTest() {

		//given
		Long userId = 1L;
		User user = createUser();
		Long postId = 1L;
		Post post = createPost(user);
		PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto(userId);
		PostCreateResponseDto postCreateResponseDto = createPostCreteResponseDto(postId);

		when(userService.findById(userId)).thenReturn(user);
		when(postConverter.toPost(postCreateRequestDto, user)).thenReturn(post);
		when(postService.create(post)).thenReturn(postId);
		when(postConverter.toPostCreateResponseDto(postId)).thenReturn(postCreateResponseDto);

		//when
		PostCreateResponseDto createResponseDto = postFacade.create(postCreateRequestDto);

		//then
		verify(userService).findById(userId);
		verify(postService).create(post);
		verify(postConverter).toPost(postCreateRequestDto, user);
		verify(postConverter).toPostCreateResponseDto(postId);
		assertThat(createResponseDto).hasFieldOrPropertyWithValue("id", postId);
	}

	@Test
	@DisplayName("id를 사용하여 수정할 Post를 조회하고, Dto의 내용으로 수정한다.")
	void updateTest() {

		//given
		Long postId = 1L;
		User user = createUser();
		Post post = createPost(user);
		PostUpdateDto postUpdateDto = createPostUpdateDto();

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
		PostResponseDto postResponseDto = createPostResponseDto(postId);

		when(postService.findById(postId)).thenReturn(post);
		when(postConverter.toPostResponseDto(post)).thenReturn(postResponseDto);

		//when
		PostResponseDto findPost = postFacade.findById(postId);

		//then
		verify(postService).findById(postId);
		verify(postConverter).toPostResponseDto(post);

		assertThat(findPost)
			.hasFieldOrPropertyWithValue("id", postId)
			.hasFieldOrPropertyWithValue("title", postResponseDto.getTitle())
			.hasFieldOrPropertyWithValue("content", postResponseDto.getContent());
	}

	@Test
	@DisplayName("커서기반 페이지네이션으로 post를 조회하고 마지막 post id와 조회 가능한 post 존재 참, 거짓을 반환한다.")
	void findAllTest() {

		//given
		Long cursorId = null;
		Integer size = 3;
		User user = createUser();
		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		List<Post> postList = List.of(post3, post2, post1);
		Long lastIdOfList = 1L;
		boolean hasNext = false;

		CursorResult cursorResult = createCursorResult(postList, lastIdOfList, hasNext);

		PostRequestDto postRequestDto = createPostRequestDto(cursorId, size);
		PostResponseDtos postResponseDtos = createPostResponseDtos(cursorResult);

		when(postService.findAll(cursorId, size)).thenReturn(cursorResult);

		when(postConverter.toPostResponseDtos(cursorResult)).thenReturn(postResponseDtos);

		//when
		PostResponseDtos responseDtos = postFacade.findAll(postRequestDto);

		//then
		verify(postService).findAll(cursorId, size);
		verify(postConverter).toPostResponseDtos(cursorResult);

		assertThat(responseDtos)
			.hasFieldOrPropertyWithValue("nextCursorId", lastIdOfList)
			.hasFieldOrPropertyWithValue("hasNext", hasNext);

	}

}