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
import com.prgrms.board.domain.post.service.PostService;
import com.prgrms.board.domain.user.entity.User;
import com.prgrms.board.domain.user.repository.UserRepository;
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
}