package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;

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

		when(postFacade.create(ArgumentMatchers.any())).thenReturn(postCreteResponseDto);

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(request))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(content().json(response))
			.andExpect(jsonPath("id").value(postId))
			.andExpect(status().isCreated())
			.andDo(print());

		verify(postFacade).create(ArgumentMatchers.any());
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

		doNothing().when(postFacade).update(ArgumentMatchers.any(), ArgumentMatchers.any());

		mockMvc.perform(post("/api/v1/posts/{id}", postId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());

		verify(postFacade).update(ArgumentMatchers.any(), ArgumentMatchers.any());

	}

	@Test
	@DisplayName("전체 Post 조회 정상요청")
	void findAll() throws Exception {

		Long cursorId = 2L;
		Integer size = 1;
		PostRequestDto postRequestDto = createPostRequestDto(cursorId, size);

		Long postId = 1L;
		PostResponseDto postResponseDto = createPostResponseDto(postId);

		List<PostResponseDto> postResponseDtoList = List.of(postResponseDto);

		PostResponseDtos postResponseDtos = PostResponseDtos.builder()
			.postResponseDtoList(postResponseDtoList)
			.cursorId(cursorId)
			.hasNext(true)
			.build();

		String requestJson = objectMapper.writeValueAsString(postRequestDto);
		String responseJson = objectMapper.writeValueAsString(postResponseDtos);

		when(postFacade.findAll(ArgumentMatchers.any())).thenReturn(postResponseDtos);

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(content().json(responseJson))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.postResponseDtoList.[0].id").value(postId))
			.andExpect(
				jsonPath("$.postResponseDtoList.[0].title").value(postResponseDto.getTitle()))
			.andExpect(
				jsonPath("$.postResponseDtoList.[0].content").value(postResponseDto.getContent()))
			.andExpect(jsonPath("$.cursorId").value(postResponseDtos.getCursorId()))
			.andExpect(jsonPath("$.hasNext").value(postResponseDtos.getHasNext()))
			.andDo(print());

		verify(postFacade).findAll(ArgumentMatchers.any());
	}
}