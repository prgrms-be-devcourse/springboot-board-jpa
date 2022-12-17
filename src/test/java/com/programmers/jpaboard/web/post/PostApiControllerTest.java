package com.programmers.jpaboard.web.post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.dto.PostUpdateRequestDto;
import com.programmers.jpaboard.domain.post.service.PostService;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.domain.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostApiControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	PostService postService;

	@Autowired
	UserRepository userRepository;

	PostCreateRequestDto postCreateRequestDto;

	@BeforeEach
	void setUp() {
		User user = new User("권성준", "google@gmail.com", 26, "취미");
		User savedUser = userRepository.save(user);
		postCreateRequestDto = new PostCreateRequestDto("제목", "내용입니다.", savedUser.getId());
	}

	@AfterEach
	void tearDown() {
		userRepository.deleteAll();
	}

	@Test
	void createPost() throws Exception {

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postCreateRequestDto)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-save",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 ID")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
					fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정시간")
				)));
	}

	@Test
	void getPosts() throws Exception {
		mockMvc.perform(get("/api/v1/posts")
				.param("page", String.valueOf(0))
				.param("size", String.valueOf(2))
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void getPostById() throws Exception {
		PostResponseDto createdPost = postService.createPost(postCreateRequestDto);

		mockMvc.perform(get("/api/v1/posts/{postId}", createdPost.getId())
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-get",
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
					fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정시간")
				)));
	}

	@Test
	void updatePost() throws Exception {
		PostResponseDto createdPost = postService.createPost(postCreateRequestDto);
		PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("수정된 제목", "수정된 내용입니다");
		PostResponseDto updatedPost = postService.updatePost(createdPost.getId(), postUpdateRequestDto);

		mockMvc.perform(patch("/api/v1/posts/{postId}", updatedPost.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postUpdateRequestDto)))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-update",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
					fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정시간")
				)));
	}
}