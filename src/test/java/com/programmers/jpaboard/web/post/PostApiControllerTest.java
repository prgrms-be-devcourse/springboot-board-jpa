package com.programmers.jpaboard.web.post;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.dto.PostUpdateRequestDto;
import com.programmers.jpaboard.domain.post.service.PostService;

@WebMvcTest(PostApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostApiControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@InjectMocks
	PostApiController postApiController;

	@MockBean
	PostService postService;

	private PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("제목", "내용입니다.", 1L);
	private PostResponseDto createdPostDto = new PostResponseDto(
		1L,
		postCreateRequestDto.getTitle(),
		postCreateRequestDto.getContent(),
		postCreateRequestDto.getUserId(),
		LocalDateTime.now(),
		LocalDateTime.now()
	);

	@Test
	void createPost() throws Exception {

		String requestBody = objectMapper.writeValueAsString(postCreateRequestDto);
		when(postService.createPost(any(PostCreateRequestDto.class))).thenReturn(createdPostDto);

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(jsonPath("id").value(createdPostDto.getId()))
			.andExpect(jsonPath("title").value(createdPostDto.getTitle()))
			.andExpect(jsonPath("content").value(createdPostDto.getContent()))
			.andExpect(jsonPath("userId").value(createdPostDto.getUserId()))
			.andExpect(status().isCreated())
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

		verify(postService).createPost(any(PostCreateRequestDto.class));
	}

	@Test
	void getPosts() throws Exception {
		mockMvc.perform(get("/api/v1/posts")
				.param("page", String.valueOf(0))
				.param("size", String.valueOf(2)))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@Test
	void getPostById() throws Exception {

		when(postService.getPostById(any(Long.TYPE))).thenReturn(createdPostDto);

		mockMvc.perform(get("/api/v1/posts/{id}", createdPostDto.getId())
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("id").value(createdPostDto.getId()))
			.andExpect(jsonPath("title").value(createdPostDto.getTitle()))
			.andExpect(jsonPath("content").value(createdPostDto.getContent()))
			.andExpect(jsonPath("userId").value(createdPostDto.getUserId()))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-get",
				pathParameters(
					parameterWithName("id").description("조회할 게시글 ID")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 작성자 Id"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 생성시간"),
					fieldWithPath("lastModifiedAt").type(JsonFieldType.STRING).description("게시글 최종 수정시간")
				)));

		verify(postService).getPostById(any(Long.TYPE));
	}

	@Test
	void updatePost() throws Exception {
		PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto(
			"수정된 제목",
			"수정된 내용입니다"
		);
		PostResponseDto updatedPostDto = new PostResponseDto(
			1L,
			postUpdateRequestDto.getTitle(),
			postUpdateRequestDto.getContent(),
			postCreateRequestDto.getUserId(),
			createdPostDto.getCreatedAt(),
			LocalDateTime.now()
		);
		String requestBody = objectMapper.writeValueAsString(postUpdateRequestDto);
		when(postService.updatePost(eq(createdPostDto.getId()), any(PostUpdateRequestDto.class)))
			.thenReturn(updatedPostDto);

		mockMvc.perform(patch("/api/v1/posts/{id}", updatedPostDto.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(jsonPath("id").value(updatedPostDto.getId()))
			.andExpect(jsonPath("title").value(updatedPostDto.getTitle()))
			.andExpect(jsonPath("content").value(updatedPostDto.getContent()))
			.andExpect(jsonPath("userId").value(updatedPostDto.getUserId()))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-update",
				pathParameters(
					parameterWithName("id").description("수정할 게시글 ID")
				),
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

		verify(postService).updatePost(eq(createdPostDto.getId()), any(PostUpdateRequestDto.class));
	}
}