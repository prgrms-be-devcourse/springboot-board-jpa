package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static com.prgrms.devcourse.springjpaboard.domain.post.PostObjectProvider.*;
import static com.prgrms.devcourse.springjpaboard.domain.user.UserObjectProvider.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;
import com.prgrms.devcourse.springjpaboard.global.error.ErrorResponse;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerDocTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private PostFacade postFacade;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private User user = createUser();

	@BeforeAll
	void setUp() {
		userRepository.save(user);
	}

	@AfterEach
	void tearDown() {
		postRepository.deleteAll();
	}

	@Test
	@DisplayName("저장 api")
	void create() throws Exception {

		PostCreateRequest postCreateRequest = createPostCreateRequest(user.getId());

		String json = objectMapper.writeValueAsString(postCreateRequest);

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("post-create",

				requestFields(

					fieldWithPath("userId")
						.type(JsonFieldType.NUMBER)
						.description("Post 작성 User id"),

					fieldWithPath("title")
						.type(JsonFieldType.STRING)
						.description("Post 제목"),

					fieldWithPath("content")
						.type(JsonFieldType.STRING)
						.description("Post 내용")
				)
			));

	}

	@Test
	@DisplayName("단건 조회 api")
	void findById() throws Exception {

		Post post = createPost(user);

		postRepository.save(post);

		PostSearchResponse postResponseDto = postFacade.findById(post.getId());

		String json = objectMapper.writeValueAsString(postResponseDto);

		mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
			.andExpect(status().isOk())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("id").value(post.getId()))
			.andExpect(jsonPath("title").value(postResponseDto.getTitle()))
			.andExpect(jsonPath("content").value(postResponseDto.getContent()))
			.andExpect(jsonPath("createdAt").value(
				postResponseDto.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))))
			.andDo(print())
			.andDo(document("post-findById",

				pathParameters(

					parameterWithName("id")
						.description("조회할 Post id")
				),

				responseFields(

					fieldWithPath("id").type(JsonFieldType.NUMBER).description("조회된 Post id"),

					fieldWithPath("title")
						.type(JsonFieldType.STRING)
						.description("조회된 title"),

					fieldWithPath("content")
						.type(JsonFieldType.STRING)
						.description("조회된 Post content"),

					fieldWithPath("createdAt")
						.type(JsonFieldType.STRING)
						.description("조회된 Post content 생성시간")

				)
			));

	}

	@Test
	@DisplayName("단건 조회 실패")
	void findByIdFail() throws Exception {

		Long postId = 100L;

		ErrorResponse errorResponse = new ErrorResponse("존재하지 않는 Post입니다.");

		String json = objectMapper.writeValueAsString(errorResponse);

		mockMvc.perform(get("/api/v1/posts/{id}", postId))
			.andExpect(status().isNotFound())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value(errorResponse.getMessage()))
			.andDo(print())
			.andDo(document("post-findById-notfound",

				pathParameters(

					parameterWithName("id")
						.description("조회할 Post id")
				),

				responseFields(

					fieldWithPath("message")
						.type(JsonFieldType.STRING)
						.description("에러 메시지 내용")
				)

			));

	}

	@Test
	@DisplayName("수정 api")
	void update() throws Exception {

		Post post = createPost(user);

		postRepository.save(post);

		PostUpdateRequest postUpdateRequest = createPostUpdateRequest();

		String json = objectMapper.writeValueAsString(postUpdateRequest);

		mockMvc.perform(patch("/api/v1/posts/{id}", post.getId())
				.content(json)
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("post-update",

				pathParameters(

					parameterWithName("id")
						.description("수정할 Post id")
				),

				requestFields(

					fieldWithPath("title")
						.type(JsonFieldType.STRING)
						.description("수정할 Post title"),

					fieldWithPath("content")
						.type(JsonFieldType.STRING)
						.description("수정할 Post content")
				)));
	}

	@Test
	@DisplayName("전체 조회 api")
	void findAll() throws Exception {

		List<Post> postList = createPostList(user);

		postRepository.saveAll(postList);

		Long cursorId = 15L;
		Integer size = 3;

		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("cursorId", String.valueOf(cursorId));
		params.add("size", String.valueOf(size));

		mockMvc.perform(get("/api/v1/posts")
				.params(params))
			.andExpect(status().isOk())
			.andExpect(jsonPath("hasNext").value(true))
			.andExpect(jsonPath("posts").exists())

			.andDo(print())
			.andDo(document("post-findAll",

					requestParameters(

						parameterWithName("cursorId")
							.description("현재 cursorId"),

						parameterWithName("size")
							.description("조회 size")
					),

					responseFields(

						fieldWithPath("posts.[].id")
							.type(JsonFieldType.NUMBER)
							.description("조회된 Post id"),

						fieldWithPath("posts.[].title")
							.type(JsonFieldType.STRING)
							.description("조회된 Post title"),

						fieldWithPath("posts.[].content")
							.type(JsonFieldType.STRING)
							.description("조회된 Post content"),

						fieldWithPath("posts.[].createdAt")
							.type(JsonFieldType.STRING)
							.description("조회된 Post createdAt"),

						fieldWithPath("hasNext")
							.type(JsonFieldType.BOOLEAN)
							.description("다음에 조회할 Post 존재 유무")

					)

				)
			);

	}
}
