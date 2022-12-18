package com.prgrms.devcourse.springjpaboard.domain.post.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.post.repository.PostRepository;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
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

	public User createUser() {

		return User.builder()
			.name("geonwoo")
			.age(25)
			.hobby("basketball")
			.build();

	}

	public Post createPost(User user) {

		return Post.builder()
			.user(user)
			.title("hello")
			.content("hi")
			.build();

	}

	@Test
	@DisplayName("저장 api")
	void create() throws Exception {

		User user = createUser();

		userRepository.save(user);

		PostSaveDto postSaveDto = PostSaveDto.builder()
			.userId(user.getId())
			.title("hello")
			.content("hi")
			.build();

		String json = objectMapper.writeValueAsString(postSaveDto);

		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(status().isOk())
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

		User user = createUser();

		userRepository.save(user);

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
						.description("조회된 Post title"),

					fieldWithPath("content")
						.type(JsonFieldType.STRING)
						.description("조회된 Post content")
				)
			));

	}

	@Test
	@DisplayName("수정 api")
	void update() throws Exception {

		User user = createUser();

		userRepository.save(user);

		Post post = createPost(user);

		postRepository.save(post);

		PostUpdateDto postUpdateDto = PostUpdateDto.builder()
			.title("NSYNC")
			.content("itsgonnabeme")
			.build();

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

	@Test
	@DisplayName("전체 조회 api")
	void findAll() throws Exception {

		User user = createUser();

		userRepository.save(user);

		Post post1 = createPost(user);
		Post post2 = createPost(user);
		Post post3 = createPost(user);

		postRepository.save(post1);
		postRepository.save(post2);
		postRepository.save(post3);

		Long cursorId = post3.getId();
		Integer size = 1;

		PostRequestDto postRequestDto = PostRequestDto.builder()
			.cursorId(cursorId)
			.size(size)
			.build();

		String requestJson = objectMapper.writeValueAsString(postRequestDto);

		mockMvc.perform(get("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.postResponseDtoList.[0].id").value(post2.getId()))
			.andExpect(jsonPath("$.postResponseDtoList.[0].title").value(post2.getTitle()))
			.andExpect(jsonPath("$.postResponseDtoList.[0].content").value(post2.getContent()))
			.andExpect(jsonPath("$.hasNext").value(true))
			.andExpect(jsonPath("$.cursorId").value(post2.getId()))
			.andDo(print())
			.andDo(document("post-findAll",

				requestFields(

					fieldWithPath("cursorId")
						.type(JsonFieldType.NUMBER)
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

					fieldWithPath("cursorId")
						.type(JsonFieldType.NUMBER)
						.description("현재 cursorId"),

					fieldWithPath("hasNext")
						.type(JsonFieldType.BOOLEAN)
						.description("다음 요소 존재 유무")
				)

			));

	}
}
