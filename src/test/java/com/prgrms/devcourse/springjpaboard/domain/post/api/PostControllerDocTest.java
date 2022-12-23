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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostFacade;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

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

	@BeforeEach
	void clear() {
		postRepository.deleteAll();
	}



	@Test
	@DisplayName("저장 api")
	void create() throws Exception {

		PostCreateRequestDto postSaveDto = createPostCreateRequestDto(user.getId());

		String json = objectMapper.writeValueAsString(postSaveDto);

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
	@DisplayName("조회 api")
	void findById() throws Exception {

		Post post = createPost(user);

		postRepository.save(post);

		PostResponseDto postResponseDto = postFacade.findById(post.getId());

		String json = objectMapper.writeValueAsString(postResponseDto);

		mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
			.andExpect(status().isOk())
			.andExpect(content().json(json))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(post.getId()))
			.andExpect(jsonPath("$.title").value(postResponseDto.getTitle()))
			.andExpect(jsonPath("$.content").value(postResponseDto.getContent()))
			.andExpect(jsonPath("$.createdAt").value(postResponseDto.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"))))
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
	@DisplayName("수정 api")
	void update() throws Exception {

		Post post = createPost(user);

		postRepository.save(post);

		PostUpdateDto postUpdateDto = createPostUpdateDto();

		String json = objectMapper.writeValueAsString(postUpdateDto);

		mockMvc.perform(post("/api/v1/posts/{id}", post.getId())
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

	private List<Post> createPosts(User user, int cnt) {
		List<Post> postList = new ArrayList<>();
		for (int i = 1; i <= cnt; i++) {
			postList.add(createPost("제목" + i, "title" + i, user));
		}
		return postList;
	}

	@Test
	@DisplayName("전체 조회 api")
	void findAll() throws Exception {

		List<Post> postList = createPosts(user, 3);

		postRepository.saveAll(postList);

		Collections.reverse(postList);
		for (Post post : postList) {
			System.out.println(post.getId());

		}
		Long cursorId = null;
		Integer size = 3;

		Long expectId =  postList.get(0).getId();
		String expectTitle =postList.get(0).getTitle();
		String expectContent =  postList.get(0).getContent();
		boolean expectHasNext = false;
		Long expectNextCursorId = postList.get(postList.size() - 1).getId();

		PostRequestDto postRequestDto = createPostRequestDto(cursorId, size);

		String requestJson = objectMapper.writeValueAsString(postRequestDto);

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.postResponseDtoList.[0].id").value(expectId))
			.andExpect(jsonPath("$.postResponseDtoList.[0].title").value(expectTitle))
			.andExpect(jsonPath("$.postResponseDtoList.[0].content").value(expectContent))
			.andExpect(jsonPath("$.hasNext").value(expectHasNext))
			.andExpect(jsonPath("$.nextCursorId").value(expectNextCursorId))
			.andDo(print())
			.andDo(document("post-findAll",

				requestFields(

					fieldWithPath("cursorId")
						.type(JsonFieldType.NULL)
						.description("현재 cursorId"),

					fieldWithPath("size")
						.type(JsonFieldType.NUMBER)
						.description("조회 size")
				),

				responseFields(

					fieldWithPath("postResponseDtoList.[].id")
						.type(JsonFieldType.NUMBER)
						.description("조회된 Post id"),

					fieldWithPath("postResponseDtoList.[].title")
						.type(JsonFieldType.STRING)
						.description("조회된 Post title"),

					fieldWithPath("postResponseDtoList.[].content")
						.type(JsonFieldType.STRING)
						.description("조회된 Post content"),

					fieldWithPath("postResponseDtoList.[].createdAt")
						.type(JsonFieldType.STRING)
						.description("조회된 Post createdAt"),

					fieldWithPath("nextCursorId")
						.type(JsonFieldType.NUMBER)
						.description("마지막 cursorId"),

					fieldWithPath("hasNext")
						.type(JsonFieldType.BOOLEAN)
						.description("다음 요소 존재 유무")
				)

			));

	}
}
