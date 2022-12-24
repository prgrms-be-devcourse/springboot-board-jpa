package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
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
	@DisplayName("단건 조회 정상요청")
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
	@DisplayName("Post 수정 정상요청")
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
	@DisplayName("전체 Post 조회 정상요청")
	void findAll() throws Exception {

		Long cursorId = 3L;
		Integer size = 3;

		User user = createUser();
		List<Post> postList = createPostList(user);

		Pageable pageable = PageRequest.of(0, size);

		PostSearchResponses postSearchResponses = createPostSearchResponses(postList, false);

		String responseJson = objectMapper.writeValueAsString(postSearchResponses);

		when(postFacade.findAll(cursorId, pageable)).thenReturn(postSearchResponses);

		mockMvc.perform(get("/api/v1/posts")
				.param("cursorId", String.valueOf(cursorId))
				.param("size", String.valueOf(size)))
			.andExpect(status().isOk())
			.andExpect(content().json(responseJson))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			// .andExpect(jsonPath("$.hasNext").value(postSearchResponses.getHasNext()))
			// .andExpect(jsonPath("$.posts").value(postSearchResponses.getPosts()))
			.andDo(print());

		verify(postFacade).findAll(cursorId, pageable);

	}
}