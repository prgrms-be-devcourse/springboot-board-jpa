package com.kdt.jpa.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.service.MemberService;
import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.domain.post.model.Post;
import com.kdt.jpa.domain.post.service.PostService;

@WebMvcTest
@AutoConfigureRestDocs
class PostRestControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PostService postService;

	@MockBean
	MemberService memberService;

	@Autowired
	ObjectMapper objectMapper;

	Member member;
	MemberResponse memberResponse;

	@BeforeEach
	void setup() {
		member = new Member(1L, "jinhyungPark", 27, "개발");
		memberResponse = new MemberResponse
			(
				member.getId(),
				member.getName(),
				member.getAge(),
				member.getHobby(),
				LocalDateTime.now()
			);
	}

	@Test
	@DisplayName("게시물 단건 조회 테스트")
	void getById() throws Exception {
		//given
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();

		PostResponse postResponse = new PostResponse
			(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				memberResponse,
				LocalDateTime.now()
			);
		given(postService.findById(any())).willReturn(postResponse);

		String response = objectMapper.writeValueAsString(postResponse);

		//when
		ResultActions perform = mockMvc.perform
			(RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", post.getId())
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print());

		//then
		verify(postService, times(1)).findById(any());

		perform
			.andExpect(status().isOk())
			.andExpect(content().string(response));

		perform.andDo(
			document(
				"get-post",
				pathParameters(
					parameterWithName("id").description("Post ID")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("Post ID"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
					fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
					fieldWithPath("author").type(JsonFieldType.OBJECT).description("Author"),
					fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("Author ID"),
					fieldWithPath("author.name").type(JsonFieldType.STRING).description("Author name"),
					fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("Author age"),
					fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("Author hobby"),
					fieldWithPath("author.createdAt").type(JsonFieldType.STRING).description("Author createdAt")
				)
			)
		);
	}

	@Test
	@DisplayName("게시물 조회 - 페이지 0, 사이즈 20")
	void getWithPaginationSize20() throws Exception {
		PageRequest pageRequest = PageRequest.of(0, 20);
		List<PostResponse> postResponses = getDummyPostResponses();
		PageImpl page = new PageImpl(postResponses.subList(0,20), pageRequest, postResponses.size());
		given(postService.findAll(pageRequest)).willReturn(page);

		String request = objectMapper.writeValueAsString(pageRequest);
		String response = objectMapper.writeValueAsString(page);

		//when
		ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts")
				.content(request)
				.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print());

		//then
		verify(postService, times(1)).findAll(pageRequest);

		perform.andExpect(status().isOk())
			.andExpect(content().string(response));

		perform.andDo(
			document("get-posts",
				requestFields(
					fieldWithPath("sort").type(JsonFieldType.OBJECT).description("Sort"),
					fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("Sort empty"),
					fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("Sort sorted"),
					fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("Sort unsorted"),
					fieldWithPath("offset").type(JsonFieldType.NUMBER).description("Offset"),
					fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("PageNumber"),
					fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("PageSize"),
					fieldWithPath("unpaged").type(JsonFieldType.BOOLEAN).description("Unpaged"),
					fieldWithPath("paged").type(JsonFieldType.BOOLEAN).description("Paged")
				),
				responseFields(
					fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("Contents"),
					fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("Post ID"),
					fieldWithPath("content[].title").type(JsonFieldType.STRING).description("Post Title"),
					fieldWithPath("content[].content").type(JsonFieldType.STRING).description("Post Content"),
					fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("Post createdAt"),
					fieldWithPath("content[].author").type(JsonFieldType.OBJECT).description("Post Author"),
					fieldWithPath("content[].author.id").type(JsonFieldType.NUMBER).description("Post Author ID"),
					fieldWithPath("content[].author.name").type(JsonFieldType.STRING).description("Post Author name"),
					fieldWithPath("content[].author.age").type(JsonFieldType.NUMBER).description("Post Author age"),
					fieldWithPath("content[].author.hobby").type(JsonFieldType.STRING).description("Post Author hobby"),
					fieldWithPath("content[].author.createdAt").type(JsonFieldType.STRING).description("Post Author createdAt"),
					fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("Pageable"),
					fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("Pageable offset"),
					fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("Pageable pageNumber"),
					fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("Pageable pageSize"),
					fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("Pageable unpaged"),
					fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("Pageable paged"),
					fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("Pageable sort"),
					fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("Pageable Sort empty"),
					fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("Pageable Sort sorted"),
					fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("Pageable Sort unsorted"),
					fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("Last"),
					fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("TotalElements"),
					fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("TotalPages"),
					fieldWithPath("size").type(JsonFieldType.NUMBER).description("Size"),
					fieldWithPath("number").type(JsonFieldType.NUMBER).description("Number"),
					fieldWithPath("sort").type(JsonFieldType.OBJECT).description("Sort"),
					fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("Sort empty"),
					fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("Sort sorted"),
					fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("Sort unsorted"),
					fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("First"),
					fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("NumberOfElements"),
					fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("Empty")
				)
				)
		);
	}

	@Test
	@DisplayName("게시물 작성 성공 테스트")
	void writeSuccess() throws Exception {
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest
			(
				post.getTitle(), post.getContent(), member.getId()
			);
		PostResponse.WritePostResponse writePostResponse = new PostResponse.WritePostResponse(post.getId());

		given(postService.write(any(PostRequest.WritePostRequest.class))).willReturn(writePostResponse);

		String request = objectMapper.writeValueAsString(writePostRequest);
		String response = objectMapper.writeValueAsString(writePostResponse);

		//when
		ResultActions perform = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(request))
			.andDo(print());

		//then
		verify(postService, times(1)).write(any(PostRequest.WritePostRequest.class));

		perform.andExpect(content().string(response));

		perform.andDo(document("save-post",
			requestFields(
				fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
				fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
				fieldWithPath("authorId").type(JsonFieldType.NUMBER).description("Author ID")
			),
			responseFields(
				fieldWithPath("id").type(JsonFieldType.NUMBER).description("Post ID")
			))
		);
	}

	@Test
	@DisplayName("게시물 수정 성공 테스트")
	void updateSuccess() throws Exception {
		//given
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title",
			"updated content");
		PostResponse.UpdatePostResponse updatePostResponse = new PostResponse.UpdatePostResponse(updatePostRequest.title(),
			updatePostRequest.content());
		given(postService.update(any(Long.class), any(PostRequest.UpdatePostRequest.class))).willReturn(updatePostResponse);

		String request = objectMapper.writeValueAsString(updatePostRequest);
		String response = objectMapper.writeValueAsString(updatePostResponse);

		//when
		ResultActions perform = mockMvc.perform(RestDocumentationRequestBuilders.patch("/api/v1/posts/{id}", post.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(request)
		).andDo(print());

		//then
		verify(postService, times(1)).update(any(Long.class), any(PostRequest.UpdatePostRequest.class));

		perform.andExpect(status().isOk())
			.andExpect(content().string(response));
		perform.andDo(
			document("update-post",
				pathParameters(
					parameterWithName("id").description("Post ID")
				),
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("Content")
				),
				responseFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("Content")
				)
			)
		);
	}

	private List<PostResponse> getDummyPostResponses() {
		return Stream.iterate(1, i -> i + 1).limit(30)
			.map(i -> new PostResponse(Long.valueOf(i), "title" + i, "content" + i, memberResponse, LocalDateTime.now()))
			.collect(Collectors.toList());
	}
}