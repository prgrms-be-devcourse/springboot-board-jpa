package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static com.prgrms.devcourse.springjpaboard.domain.post.TestPostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.TestUserObjectProvider.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.exeception.PostNotFoundException;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.global.error.ErrorResponse;

@WebMvcTest(PostController.class)
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostFacade postFacade;

	@Test
	@DisplayName("post 저장 정상요청")
	void createTest() throws Exception {

		Long userId = 1L;
		Long postId = 1L;

		PostCreateRequest postCreateRequestDto = createPostCreateRequest(userId);
		PostCreateResponse postCreteResponseDto = createPostCreteResponse(postId);

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
	@DisplayName("post 단건 조회 정상요청")
	void findById() throws Exception {

		Long postId = 1L;
		PostSearchResponse postSearchResponse = createPostSearchResponse(postId);

		String json = objectMapper.writeValueAsString(postSearchResponse);

		when(postFacade.findById(postId)).thenReturn(postSearchResponse);

		mockMvc.perform(get("/api/v1/posts/{id}", postId))
			.andExpect(status().isOk())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(postId))
			.andExpect(jsonPath("$.title").value(postSearchResponse.getTitle()))
			.andExpect(jsonPath("$.content").value(postSearchResponse.getContent()))
			.andDo(print());

		verify(postFacade).findById(postId);

	}

	@Test
	@DisplayName("post 단건 조회 실패")
	void findByIdFail() throws Exception {

		Long postId = 100L;

		ErrorResponse errorResponse = new ErrorResponse("존재하지 않는 Post입니다.");

		when(postFacade.findById(postId)).thenThrow(new PostNotFoundException());

		String json = objectMapper.writeValueAsString(errorResponse);

		mockMvc.perform(get("/api/v1/posts/{id}", postId))
			.andExpect(status().isNotFound())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value(errorResponse.getMessage()))
			.andDo(print());

		verify(postFacade).findById(postId);

	}

	@Test
	@DisplayName("post 수정 정상요청")
	void updateTest() throws Exception {

		Long postId = 1L;

		PostUpdateRequest postUpdateRequest = createPostUpdateRequest();

		String json = objectMapper.writeValueAsString(postUpdateRequest);

		doNothing().when(postFacade).update(postId, postUpdateRequest);

		mockMvc.perform(patch("/api/v1/posts/{id}", postId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());

		verify(postFacade).update(postId, postUpdateRequest);

	}

	@Test
	@DisplayName("전체 post 조회 정상요청")
	void findAll() throws Exception {

		Long cursorId = 15L;
		Integer size = 3;

		User user = createUser();
		List<Post> postList = createPostList(user);

		PostSearchResponses postSearchResponses = createPostSearchResponses(postList, true);

		String responseJson = objectMapper.writeValueAsString(postSearchResponses);

		when(postFacade.findAll(cursorId, size)).thenReturn(postSearchResponses);

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cursorId", String.valueOf(cursorId));
		params.add("size", String.valueOf(size));

		mockMvc.perform(get("/api/v1/posts")
				.params(params))
			.andExpect(status().isOk())
			.andExpect(content().json(responseJson))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.hasNext").value(postSearchResponses.getHasNext()))
			.andExpect(jsonPath("$.posts").exists())
			.andDo(print());

		verify(postFacade).findAll(cursorId, size);

	}
}