package com.prgrms.devcourse.springjpaboard.domain.post.application.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostService;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.UserService;

@ExtendWith(MockitoExtension.class)
class PostFacadeTest {

	@Mock
	PostService postService;

	@Mock
	UserService userService;

	@InjectMocks
	PostFacade postFacade;


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

	public PostSaveDto createPostSaveDto(Long userId) {
		return PostSaveDto.builder()
			.userId(userId)
			.title("hello")
			.content("hi~")
			.build();

	}

	public PostUpdateDto createPostUpdateDto() {
		return PostUpdateDto.builder()
			.title("hello2")
			.content("hi2")
			.build();
	}

	public PostResponseDto createPostResponseDto(Long id) {
		return PostResponseDto.builder()
			.id(id)
			.title("hello")
			.content("hi")
			.build();
	}

	public PostRequestDto createPostRequestDto(Long cursorId, Integer size) {
		return PostRequestDto.builder()
			.cursorId(cursorId)
			.size(size)
			.build();
	}

	public PostResponseDtos createPostResponseDtos(List<Post> postList, Long lastIdOfList, boolean hasNext) {
		return PostResponseDtos.builder()
			.postResponseDtoList(postList.stream().map(PostConverter::toPostResponseDto).collect(Collectors.toList()))
			.cursorId(lastIdOfList)
			.hasNext(hasNext)
			.build();
	}

	@Test
	@DisplayName("Dto를 사용하여 user를 조회하고, Dto를 Post로 변환하여 저장한다.")
	void createTest() {

		Long userId = 1L;
		MockedStatic<PostConverter> postConverterMockedStatic = Mockito.mockStatic(PostConverter.class);

		User user = createUser();
		Post post = createPost(user);
		PostSaveDto postSaveDto = createPostSaveDto(userId);

		Mockito.when(userService.findById(userId)).thenReturn(user);

		Mockito.when(PostConverter.toPost(postSaveDto, user)).thenReturn(post);
		Mockito.doNothing().when(postService).create(post);

		postFacade.create(postSaveDto);

		Mockito.verify(userService).findById(userId);
		Mockito.verify(postService).create(post);
		postConverterMockedStatic.verify(() -> PostConverter.toPost(postSaveDto, user), Mockito.times(1));

		postConverterMockedStatic.close();
	}

	@Test
	@DisplayName("id를 사용하여 수정할 Post를 조회하고, Dto의 내용으로 수정한다.")
	void updateTest() {

		Long postId = 1L;
		User user = createUser();
		Post post = createPost(user);

		MockedStatic<PostConverter> postConverterMockedStatic = Mockito.mockStatic(PostConverter.class);
		PostUpdateDto postUpdateDto = createPostUpdateDto();

		Mockito.when(PostConverter.toPost(postUpdateDto)).thenReturn(post);
		Mockito.doNothing().when(postService).update(postId, post);

		postFacade.update(postId, postUpdateDto);

		Mockito.verify(postService).update(postId, post);
		postConverterMockedStatic.verify(() -> PostConverter.toPost(postUpdateDto), Mockito.times(1));

		postConverterMockedStatic.close();
	}

	@Test
	@DisplayName("id를 사용하여 Post를 조회하여 dto로 반환한다.")
	void findById() {

		Long postId = 1L;
		User user = createUser();
		Post post = createPost(user);
		PostResponseDto postResponseDto = createPostResponseDto(postId);

		MockedStatic<PostConverter> postConverterMockedStatic = Mockito.mockStatic(PostConverter.class);
		Mockito.when(postService.findById(postId)).thenReturn(post);
		Mockito.when(PostConverter.toPostResponseDto(post)).thenReturn(postResponseDto);

		postFacade.findById(postId);

		Mockito.verify(postService).findById(postId);
		postConverterMockedStatic.verify(() -> PostConverter.toPostResponseDto(post), Mockito.times(1));

		postConverterMockedStatic.close();
	}

	@Test
	@DisplayName("전제조회")
	void findAllTest() {

		Long cursorId = null;
		Integer size = 3;
		User user = createUser();
		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		List<Post> postList = List.of(post3, post2, post1);
		Long lastIdOfList = 1L;
		boolean hasNext = false;

		PostRequestDto postRequestDto = createPostRequestDto(cursorId, size);
		PostResponseDtos postResponseDtos = createPostResponseDtos(postList, lastIdOfList, hasNext);
		MockedStatic<PostConverter> postConverterMockedStatic = Mockito.mockStatic(PostConverter.class);

		Mockito.when(postService.findAll(cursorId, size))
			.thenReturn(postList);

		Mockito.when(postService.getLastIdOfList(postList)).thenReturn(lastIdOfList);

		Mockito.when(postService.hasNext(lastIdOfList)).thenReturn(hasNext);

		Mockito.when(PostConverter.toPostResponseDtos(postList, lastIdOfList, hasNext)).thenReturn(postResponseDtos);

		postFacade.findAll(postRequestDto);

		Mockito.verify(postService).findAll(cursorId, size);
		Mockito.verify(postService).getLastIdOfList(postList);
		Mockito.verify(postService).hasNext(lastIdOfList);
		postConverterMockedStatic.verify(() -> PostConverter.toPostResponseDtos(postList, lastIdOfList, hasNext),
			Mockito.times(1));

		postConverterMockedStatic.close();

	}

}