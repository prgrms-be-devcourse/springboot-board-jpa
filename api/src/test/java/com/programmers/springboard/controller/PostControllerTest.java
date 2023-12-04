package com.programmers.springboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springboard.entity.Member;
import com.programmers.springboard.repository.MemberRepository;
import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.PostResponse;
import com.programmers.springboard.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@ActiveProfiles("dev")
public class PostControllerTest {

	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private PostService postService;
	@PersistenceContext
	private EntityManager em;

	@Test
	void 포스트_작성_성공() throws Exception {
		// given
		Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());
		PostCreateRequest request = new PostCreateRequest("test", "test", member.getId());

		// when // then
		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("post-save",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId")
				),
				responseFields(
					fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
					fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
				)
			));
	}

	@Test
	void 포스트_페이징_조회() throws Exception {
		// given
		Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());

		for (int i = 0; i < 11; i++) {
			postService.createPost(new PostCreateRequest("test", "test", member.getId()));
		}

		// when // then
		mockMvc.perform(get("/api/v1/posts")
				.param("page", "1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()", Matchers.is(10)))
			.andDo(print())
			.andDo(document("get-posts-with-paging",
				queryParameters(
					parameterWithName("page").description("page")
				),
				responseFields(
					fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("[].memberId").type(JsonFieldType.NUMBER).description("memberId"),
					fieldWithPath("[].memberName").type(JsonFieldType.STRING).description("memberName")
				)
			));

		em.flush();
	}

	@Test
	void 포스트_개별_조회() throws Exception {
		// given
		Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());
		PostResponse post = postService.createPost(new PostCreateRequest("test", "test", member.getId()));

		// when // then
		mockMvc.perform(get("/api/v1/posts/{id}", post.postId()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.postId", Matchers.is(post.postId().intValue())))
			.andExpect(jsonPath("$.title", Matchers.is(post.title())))
			.andExpect(jsonPath("$.content", Matchers.is(post.content())))
			.andExpect(jsonPath("$.memberId", Matchers.is(post.memberId().intValue())))
			.andExpect(jsonPath("$.memberName", Matchers.is(post.memberName())))
			.andDo(print())
			.andDo(document("get-post-by-id",
				responseFields(
					fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
					fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
				)
			));
	}

	@Test
	void 포스트_수정_성공() throws Exception {
		// given
		Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());
		PostResponse post = postService.createPost(new PostCreateRequest("test", "test", member.getId()));

		PostUpdateRequest postUpdateRequest = new PostUpdateRequest("fix!", "fix!");

		// when // then
		mockMvc.perform(put("/api/v1/posts/{id}",post.postId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(postUpdateRequest)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", Matchers.is(postUpdateRequest.title())))
				.andExpect(jsonPath("$.content", Matchers.is(postUpdateRequest.content())))
				.andDo(print())
				.andDo(document("post-update",
				requestFields(
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content")
				),
				responseFields(
					fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
					fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
					fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
					fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
					fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
				)
			));
	}

	@Test
	void 포스트_삭제_성공() throws Exception {
		// given
		Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());
		PostResponse post = postService.createPost(new PostCreateRequest("test", "test", member.getId()));

		// when // then
		mockMvc.perform(delete("/api/v1/posts/{id}",post.postId()))
			.andExpect(status().isNoContent())
			.andDo(print())
			.andDo(document("post-delete"));

		em.flush();
	}
}
