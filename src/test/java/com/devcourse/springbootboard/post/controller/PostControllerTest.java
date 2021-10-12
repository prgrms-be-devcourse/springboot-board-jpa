package com.devcourse.springbootboard.post.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devcourse.springbootboard.post.dto.PostDeleteResponse;
import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostUpdateRequest;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.post.service.PostService;
import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {
	private static final User USER = new User(1L, "명환", 29, new Hobby("축구"));

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private PostService postService;

	@DisplayName("게시글 페이징(다건) 조회 요청 테스트")
	@Test
	void testGetAll() throws Exception {
		// given
		List<PostResponse> postResponses = new ArrayList<>();
		IntStream.range(1, 4)
			.forEach(i -> postResponses.add(
				PostResponse.builder()
					.postId((long)i)
					.title("title")
					.content("content")
					.userId((long)i)
					.build())
			);
		Page<PostResponse> postResponsePage = new PageImpl<>(postResponses);
		given(postService.findPosts(any(Pageable.class))).willReturn(postResponsePage);

		// when
		ResultActions result = mockMvc.perform(
			get("/posts")
				.param("page", "1")
				.param("size", "5")
		);

		// then
		result.andExpect(status().isOk())
			.andDo(document(
				"get-posts",
				requestParameters(
					parameterWithName("page").description("페이지 번호"),
					parameterWithName("size").description("게시글 갯수")
				),
				responseFields(
					fieldWithPath("content[].postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
					fieldWithPath("content[].title").type(JsonFieldType.STRING).description("글 제목"),
					fieldWithPath("content[].content").type(JsonFieldType.STRING).description("글 내용"),
					fieldWithPath("content[].userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
					fieldWithPath("pageable").type(JsonFieldType.STRING).description("pageable"),
					fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
					fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("전체 게시글 갯수"),
					fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
					fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지에서 보여줄 게시글 갯수"),
					fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지의 개시글 갯수"),
					fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
					fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
					fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("리스트가 비어있는지 여부"),
					fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
					fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
					fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty")
				)
			));
	}

	@DisplayName("게시글 단건 조회 요청 테스트")
	@Test
	void testGetPost() throws Exception {
		// given
		given(postService.findPost(anyLong())).willReturn(new PostResponse(1L, "제목", "내용", 1L));

		// when
		ResultActions result = mockMvc.perform(
			get("/posts/{id}", 1)
				.accept(MediaType.APPLICATION_JSON)
		);

		// then
		result.andExpect(status().isOk())
			.andDo(document(
				"get-post",
				pathParameters(
					parameterWithName("id").description("게시글 아이디")
				),
				responseFields(
					fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
				)
			));
	}

	@DisplayName("게시글 작성 테스트")
	@Test
	void testAddPost() throws Exception {
		// given
		given(postService.savePost(any(PostWriteRequest.class))).willReturn(new PostResponse(1L, "제목", "내용", 1L));

		// when
		PostWriteRequest postWriteRequest = new PostWriteRequest("제목", "내용", 1L);
		ResultActions result = mockMvc.perform(
			post("/posts")
				.content(objectMapper.writeValueAsString(postWriteRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(document(
				"add-post",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
				),
				responseFields(
					fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("글 제목"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("글 내용"),
					fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
				)
			));

	}

	@DisplayName("게시글 수정 요청 테스트")
	@Test
	void testModifyPost() throws Exception {
		// given
		given(postService.updatePost(any(PostUpdateRequest.class))).willReturn(new PostResponse(1L, "제목", "내용", 1L));

		// when
		PostUpdateRequest postUpdateRequest = new PostUpdateRequest(1L, "수정된 제목", "수정된 내용", 1L);
		ResultActions result = mockMvc.perform(
			put("/posts")
				.content(objectMapper.writeValueAsString(postUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"modify-post",
					requestFields(
						fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 내용"),
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
					),
					responseFields(
						fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
						fieldWithPath("title").type(JsonFieldType.STRING).description("수정된 제목"),
						fieldWithPath("content").type(JsonFieldType.STRING).description("수정된 내용"),
						fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
					)
				));
	}

	@DisplayName("게시글 삭제 요청 테스트")
	@Test
	void testRemovePost() throws Exception {
		// given
		given(postService.deletePost(anyLong())).willReturn(new PostDeleteResponse(1L));

		// when
		ResultActions result = mockMvc.perform(
			delete("/posts/{id}", 1)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"remove-post",
					pathParameters(
						parameterWithName("id").description("게시글 아이디")
					),
					responseFields(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 게시글 아이디")
					)
				));
	}
}
