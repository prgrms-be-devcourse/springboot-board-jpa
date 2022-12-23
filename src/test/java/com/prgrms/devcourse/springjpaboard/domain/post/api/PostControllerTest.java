package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

@WebMvcTest(PostController.class)
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostFacade postFacade;

	@Test
	@DisplayName("저장 정상요청")
	void createTest() throws Exception {

		Long userId = 1L;
		Long postId = 1L;

		PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto(userId);
		PostCreateResponseDto postCreteResponseDto = createPostCreteResponseDto(postId);

		String request = objectMapper.writeValueAsString(postCreateRequestDto);
		String response = objectMapper.writeValueAsString(postCreteResponseDto);

		when(postFacade.create(postCreateRequestDto)).thenReturn(postCreteResponseDto);

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(response))
			.andExpect(jsonPath("id").value(postId))
			.andExpect(status().isCreated())
			.andDo(print());

		verify(postFacade).create(postCreateRequestDto);
	}

	@Test
	@DisplayName("단건 조회 정상요청")
	void findById() throws Exception {

		Long postId = 1L;
		PostResponseDto postResponseDto = createPostResponseDto(postId);

		String json = objectMapper.writeValueAsString(postResponseDto);

		when(postFacade.findById(postId)).thenReturn(postResponseDto);

		mockMvc.perform(get("/api/v1/posts/{id}", postId))
			.andExpect(status().isOk())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(postId))
			.andExpect(jsonPath("$.title").value(postResponseDto.getTitle()))
			.andExpect(jsonPath("$.content").value(postResponseDto.getContent()))
			.andDo(print());

		verify(postFacade).findById(postId);

	}

	@Test
	@DisplayName("Post 수정 정상요청")
	void updateTest() throws Exception {

		Long postId = 1L;

		PostUpdateDto postUpdateDto = createPostUpdateDto();

		String json = objectMapper.writeValueAsString(postUpdateDto);

		doNothing().when(postFacade).update(postId, postUpdateDto);

		mockMvc.perform(post("/api/v1/posts/{id}", postId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());

		verify(postFacade).update(postId, postUpdateDto);

	}

	@Test
	@DisplayName("전체 Post 조회 정상요청")
	void findAll() throws Exception {

		Long cursorId = null;
		Integer size = 3;
		PostRequestDto postRequestDto = createPostRequestDto(cursorId, size);

		User user = createUser();
		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		List<Post> postList = List.of(post3, post2, post1);

		Long lastIdOfList = 1L;
		boolean hasNext = false;

		CursorResult cursorResult = createCursorResult(postList, lastIdOfList, hasNext);

		PostResponseDtos postResponseDtos = createPostResponseDtos(cursorResult);

		String requestJson = objectMapper.writeValueAsString(postRequestDto);
		String responseJson = objectMapper.writeValueAsString(postResponseDtos);

		when(postFacade.findAll(any(PostRequestDto.class))).thenReturn(postResponseDtos);

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(content().json(responseJson))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.postResponseDtoList.[0].id").value(post3.getId()))
			.andExpect(
				jsonPath("$.postResponseDtoList.[0].title").value(post3.getTitle()))
			.andExpect(
				jsonPath("$.postResponseDtoList.[0].content").value(post3.getContent()))
			.andExpect(jsonPath("$.nextCursorId").value(postResponseDtos.getNextCursorId()))
			.andExpect(jsonPath("$.hasNext").value(postResponseDtos.getHasNext()))
			.andDo(print());

		verify(postFacade).findAll(any(PostRequestDto.class));

	}
}