package com.prgrms.devcourse.springjpaboard.domain.post.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.facade.PostFacade;

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

		PostSaveDto postSaveDto = PostSaveDto.builder()
			.userId(userId)
			.title("hello")
			.content("hi")
			.build();

		String json = objectMapper.writeValueAsString(postSaveDto);

		Mockito.doNothing().when(postFacade).create(postSaveDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	@DisplayName("단건 조회 정상요청")
	void findById() throws Exception {

		Long postId = 1L;

		PostResponseDto postResponseDto = PostResponseDto.builder()
			.id(postId)
			.title("hello")
			.content("hi")
			.build();

		String json = objectMapper.writeValueAsString(postResponseDto);

		Mockito.when(postFacade.findById(postId)).thenReturn(postResponseDto);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/{id}", postId))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(json))
			.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId))
			.andExpect(MockMvcResultMatchers.jsonPath("$.title").value(postResponseDto.getTitle()))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content").value(postResponseDto.getContent()))
			.andDo(MockMvcResultHandlers.print());

	}

	@Test
	@DisplayName("Post 수정 정상요청")
	void editTest() throws Exception {

		Long postId = 1L;

		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.title("NSYNC")
			.content("itsgonnabeme")
			.build();

		String json = objectMapper.writeValueAsString(postUpdateDto);

		Mockito.doNothing().when(postFacade).update(postId, postUpdateDto);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts/{id}", postId)
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andDo(MockMvcResultHandlers.print());

	}

	// @Test
	// @DisplayName("전체 Post 조회 정상요청")
	// void findAll() throws Exception {
	//
	// 	Long cursorId = 2L;
	// 	Integer size = 1;
	//
	// 	PostRequestDto postRequestDto = PostRequestDto.builder()
	// 		.cursorId(cursorId)
	// 		.size(size)
	// 		.build();
	//
	// 	Long postId = 1L;
	//
	// 	PostResponseDto postResponseDto = PostResponseDto.builder()
	// 		.id(postId)
	// 		.title("hello")
	// 		.content("hi")
	// 		.build();
	//
	// 	List<PostResponseDto> postResponseDtoList = List.of(postResponseDto);
	//
	// 	PostResponseDtos postResponseDtos = PostResponseDtos.builder()
	// 		.postResponseDtoList(postResponseDtoList)
	// 		.cursorId(cursorId)
	// 		.hasNext(true)
	// 		.build();
	//
	//
	// 	String requestJson = objectMapper.writeValueAsString(postRequestDto);
	// 	String responseJson = objectMapper.writeValueAsString(postResponseDtos);
	//
	// 	System.out.println(responseJson);
	//
	// 	Mockito.when(postFacade.findAll(postRequestDto)).thenReturn(postResponseDtos);
	//
	// 	mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
	// 			.contentType(MediaType.APPLICATION_JSON)
	// 			.content(requestJson))
	// 		.andExpect(MockMvcResultMatchers.status().isOk())
	// 		.andExpect(MockMvcResultMatchers.content().json(requestJson))
	// 		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
	// 		.andExpect(MockMvcResultMatchers.jsonPath("$.hasNext").value(postResponseDtos.getHasNext()))
	// 		.andExpect(MockMvcResultMatchers.jsonPath("$.cursorId").value(postResponseDtos.getCursorId()))
	// 		.andDo(MockMvcResultHandlers.print());
	//
	//
	// }
}