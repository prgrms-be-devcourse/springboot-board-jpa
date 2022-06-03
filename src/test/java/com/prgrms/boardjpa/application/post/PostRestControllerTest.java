package com.prgrms.boardjpa.application.post;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.application.post.controller.PostRestController;
import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.service.PostService;
import com.prgrms.boardjpa.application.user.model.Hobby;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.core.commons.api.SuccessResponse;
import com.prgrms.boardjpa.core.commons.page.SimplePage;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@WebMvcTest(PostRestController.class)
public class PostRestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostService postService;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private Validator validator;

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 없는 경우 디폴트 페이지를 적용한다")
	void withNullPageableRequestParam() throws Exception {
		this.mockMvc.perform(
			get("/api/posts")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(this.postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();
		PageRequest defaultPage = SimplePage.defaultPageRequest();

		assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(defaultPage);
	}

	@Test
	@DisplayName("페이징 관련 요청 파라미터가 있는 경우 전달된 파라미터로 역직렬화된 페이지를 리졸빙한다")
	void withNonNullPageableRequestParam() throws Exception {
		this.mockMvc.perform(
			get("/api/posts")
				.param("page", "0")
				.param("size", "2")
		).andExpect(status().isOk());

		ArgumentCaptor<Pageable> pageableCaptor =
			ArgumentCaptor.forClass(Pageable.class);

		verify(this.postService).getAllByPaging(pageableCaptor.capture());

		Pageable pageRequest = pageableCaptor.getValue();

		assertThat(pageRequest)
			.usingRecursiveComparison()
			.isEqualTo(
				PageRequest.of(0, 2)
			);
	}

	@Test
	@DisplayName("제목이 비어있는 게시글 생성 요청 DTO 에 대한 빈 검증결과, 1개의 제약조건 위반이 발생한다")
	public void test_Dto() {
		PostDto.CreatePostRequest postCreateRequest =
			new PostDto.CreatePostRequest("  ", 1L, "    content");

		Set<ConstraintViolation<PostDto.CreatePostRequest>> constraintViolations = validator.validate(
			postCreateRequest);

		assertThat(constraintViolations.size())
			.isEqualTo(1);
	}

	@Test
	@DisplayName("컨텐츠가 비어있는 게시글 생성 요청 DTO 를 포함한 요청이 올 경우, BAD_REQUEST 로 응답한다")
	public void with_violatedDto() throws Exception {
		PostDto.CreatePostRequest postCreateRequest =
			new PostDto.CreatePostRequest("title01", 1L, "    ");

		MvcResult mvcResult = this.mockMvc.perform(
				post("/api/posts/")
					.contentType(MediaType.APPLICATION_JSON)
					.content(this.objectMapper.writeValueAsString(postCreateRequest)))
			.andExpect(status().isBadRequest())
			.andReturn();
	}

	@Test
	@DisplayName("성공적인 응답 객체 직렬화에 성공한다")
	public void with_SuccessResponse() throws Exception {
		PostDto.PostInfo postInfo = PostDto.PostInfo.from(Post.builder()
			.id(1L)
			.content("content")
			.title("title")
			.writer(createUser())
			.build());

		Mockito.when(this.postService.getById(1L))
			.thenReturn(postInfo);

		MvcResult mvcResult = this.mockMvc.perform(
				get("/api/posts/{postId}", 1L))
			.andExpect(status().isOk())
			.andReturn();

		SuccessResponse<PostDto.PostInfo> expectedResponse = SuccessResponse.of(postInfo);

		String actualResponseBody = mvcResult.getResponse()
			.getContentAsString();

		assertThat(actualResponseBody)
			.isEqualToIgnoringWhitespace(
				this.objectMapper.writeValueAsString(expectedResponse)
			);
	}

	@Nested
	@DisplayName("RestDocs 문서화 테스트")
	public class Restdocs {
		@Test
		@DisplayName("게시글 생성 테스트")
		void test_creatingPost() throws Exception {
			User writer = createUser();
			PostDto.CreatePostRequest createPostRequest =
				new PostDto.CreatePostRequest("title01", writer.getId(), "content");
			Post createdPost = createPostRequest.createPost(writer);

			Mockito.when(
					postService.store(createPostRequest.title(), createPostRequest.writerId(), createPostRequest.content()))
				.thenReturn(PostDto.PostInfo.from(createdPost));

			mockMvc.perform(post("/api/posts")
					.content(objectMapper.writeValueAsString(createPostRequest))
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is2xxSuccessful())
				.andDo(
					document(
						"post-create",
						requestFields(
							fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
							fieldWithPath("writerId").type(JsonFieldType.NUMBER).description("작성자 ID"),
							fieldWithPath("content").type(JsonFieldType.STRING).description("본문")
						),
						responseFields(
							fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
							fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
							fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
							fieldWithPath("data.writerName").type(JsonFieldType.STRING).description("작성자 이름")
						)
					));
		}

		@Test
		@DisplayName("게시글 편집 테스트")
		void test_updatePost() throws Exception {
			User writer = createUser();
			Post post = Post.builder()
				.id(1L)
				.content("content01")
				.title("title01")
				.writer(writer)
				.build();
			PostDto.UpdatePostRequest updateRequest = new PostDto.UpdatePostRequest(post.getId(), "updatedTitle01",
				"Updated content");

			post.edit(updateRequest.title(), updateRequest.content());

			given(postService.edit(updateRequest.title(), updateRequest.id(), updateRequest.content()))
				.willReturn(PostDto.PostInfo.from(post));

			mockMvc.perform(
					put("/api/posts/{postId}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateRequest))
				).andExpect(status().is2xxSuccessful())
				.andDo(
					document(
						"post-update",
						// pathParameters(
						// 	parameterWithName("postId").description("게시글 아이디")
						// ),
						requestFields(
							fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
							fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
							fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문")
						),
						responseFields(
							fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
							fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
							fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
							fieldWithPath("data.writerName").type(JsonFieldType.STRING).description("작성자 이름")
						)
					));
		}

		@Test
		@DisplayName("게시글 페이징 테스트")
		void test_pagingPosts() throws Exception {
			PageRequest pageRequest = PageRequest.of(0, 10);
			User writer = createUser();
			Post post1 = Post.builder().
				writer(writer)
				.content("content01")
				.title("title01")
				.id(1L)
				.build();
			Post post2 = Post.builder().
				writer(writer)
				.content("content02")
				.title("title02")
				.id(2L)
				.build();

			List<PostDto.PostInfo> posts
				= List.of(PostDto.PostInfo.from(post1), PostDto.PostInfo.from(post2));

			when(postService.getAllByPaging(pageRequest))
				.thenReturn(posts);

			mockMvc.perform(
					get("/api/posts")
						.param("size", Integer.toString(pageRequest.getPageSize()))
						.param("page", Integer.toString(pageRequest.getPageNumber()))
						.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is2xxSuccessful())
				.andDo(
					document(
						"post-posts",
						requestParameters(
							parameterWithName("page").description("요청 페이지"),
							parameterWithName("size").description("페이지의 사이즈")
						),
						responseFields(
							fieldWithPath("data").type(JsonFieldType.ARRAY).description("데이터"),
							fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
							fieldWithPath("data[].content").type(JsonFieldType.STRING).description("본문"),
							fieldWithPath("data[].writerName").type(JsonFieldType.STRING).description("작성자 이름")
						)
					));
		}

		@Test
		@DisplayName("하나의 게시글 조회 테스트")
		void test_getOnePost() throws Exception {
			User writer = createUser();
			Post post = Post.builder()
				.id(1L)
				.content("content01")
				.title("title01")
				.writer(writer)
				.build();

			given(postService.getById(post.getId()))
				.willReturn(PostDto.PostInfo.from(post));

			mockMvc.perform(
					get("/api/posts/{postId}", 1L)
						.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().is2xxSuccessful())
				.andDo(
					document(
						"post-showOne",
						responseFields(
							fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
							fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
							fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
							fieldWithPath("data.writerName").type(JsonFieldType.STRING).description("작성자 이름")
						)
					));
		}
	}

	private User createUser() {
		return User.builder()
			.email("abc@naver.com")
			.hobby(Hobby.MOVIE)
			.name("writer")
			.password("abc")
			.age(27)
			.id(1L)
			.build();
	}
}
