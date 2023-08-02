package com.prgrms.board.domain.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostUpdateRequest;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.repository.PostRepository;
import com.prgrms.board.domain.post.service.PostService;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.repository.UserRepository;
import com.prgrms.board.support.PostFixture;
import com.prgrms.board.support.UserFixture;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class ApiPostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	PostService postService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	User user;

	@BeforeEach
	void init() {
		user = UserFixture.create();
		userRepository.save(user);
	}

	@Test
	@DisplayName("게시물 생성에 성공한다.")
	void create_post_success() throws Exception {
		// given
		PostCreateRequest request = new PostCreateRequest(user.getId(), "title", "content");

		// when & then
		mockMvc.perform(post("/api/v1/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andDo(print())
			.andDo(document("posts/createPost"))
			.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("게시물 전체 조회에 성공한다.")
	void get_posts_success() throws Exception {
		// given
		postRepository.save(PostFixture.create(user, "Title1", "내용1"));
		postRepository.save(PostFixture.create(user, "Title2", "내용2"));
		postRepository.save(PostFixture.create(user, "Title3", "내용3"));

		// when & then
		mockMvc.perform(get("/api/v1/posts")
				.param("page", "1")
				.param("size", "2")
			)
			.andDo(print())
			.andDo(document("posts/getPosts"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시물 조회에 성공한다.")
	void get_post_success() throws Exception {
		// given
		Post post = PostFixture.create(user, "제목", "내용");
		postRepository.save(post);

		// when & then
		mockMvc.perform(get("/api/v1/posts/" + post.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(post))
			)
			.andDo(print())
			.andDo(document("posts/getPost"))
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("게시물 수정에 성공한다.")
	void update_post_success() throws Exception {
		// given
		Post post = PostFixture.create(user, "제목", "내용");
		postRepository.save(post);
		PostUpdateRequest request = new PostUpdateRequest("수정한 제목", "수정한 내용");

		// when & then
		mockMvc.perform(patch("/api/v1/posts/" + post.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
			)
			.andDo(print())
			.andDo(document("posts/updatePost"))
			.andExpect(status().isOk());
	}
}