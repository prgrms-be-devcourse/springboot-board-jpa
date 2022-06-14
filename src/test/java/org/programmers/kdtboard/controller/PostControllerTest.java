package org.programmers.kdtboard.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.kdtboard.dto.PostDto;
import org.programmers.kdtboard.dto.UserDto;
import org.programmers.kdtboard.service.PostService;
import org.programmers.kdtboard.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private final Long USER_ID = 1L;
	private final Long POST_ID = 2L;

	private final UserDto.CreateRequest create = new UserDto.CreateRequest("혜빈", 25, "잠자기");
	private final PostDto.CreateRequest postCreateRequestRequest = new PostDto.CreateRequest("title", "content",
		USER_ID);

	@BeforeAll
	void setUp() {
		userService.create(create.name(), create.age(), create.hobby());
		postService.create(postCreateRequestRequest.title(), postCreateRequestRequest.content(), postCreateRequestRequest.userId());
	}

	@Test
	@DisplayName("post create 성공시 200 코드, postResponseDto가 return 되어야함")
	void createPost() throws Exception {
		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postCreateRequestRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.title").value(postCreateRequestRequest.title()))
			.andExpect(jsonPath("$.data.content").value(postCreateRequestRequest.content()))
			.andDo(print())
			.andDo(document("writePost",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
				),
				responseFields(
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
					fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
					fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
					fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus")
				)
			));
	}

	@Test
	@DisplayName("post findById 성공시 200 코드, 해당 postResponseDto가 return 되어야함")
	void findById() throws Exception {
		mockMvc.perform(get("/api/v1/post/{id}", POST_ID)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.title").value(postCreateRequestRequest.title()))
			.andExpect(jsonPath("$.data.content").value(postCreateRequestRequest.content()))
			.andDo(print())
			.andDo(document("getPostById",
				requestFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId")
				),
				responseFields(
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
					fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
					fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
					fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus")
				)
			));
	}

	@Test
	@DisplayName("post page 성공시 200코드, 해당 postResponseDto가 return 되어야함")
	void findAll() throws Exception {
		var pageable = Pageable.ofSize(1).withPage(0);
		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pageable)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.content[0].title").value(postCreateRequestRequest.title()))
			.andExpect(jsonPath("$.data.content[0].content").value(postCreateRequestRequest.content()));
	}

	@Test
	@DisplayName("update 성공시 200코드, update된 postResponseDto가 return 되어야함")
	void update() throws Exception {
		var updateRequestDto = new PostDto.UpdateRequest("update title", "update content");
		mockMvc.perform(put("/api/v1/posts/{id}", POST_ID)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequestDto)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.title").value(updateRequestDto.title()))
			.andExpect(jsonPath("$.data.content").value(updateRequestDto.content()))
			.andDo(print())
			.andDo(document("updatePost",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content")
				),
				responseFields(
					fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
					fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt"),
					fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
					fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus")
				)
			));
	}
}