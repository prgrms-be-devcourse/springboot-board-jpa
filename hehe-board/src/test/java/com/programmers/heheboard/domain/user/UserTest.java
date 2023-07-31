package com.programmers.heheboard.domain.user;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@AutoConfigureRestDocs(uriScheme = "https")
class UserTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void create() throws Exception {
		// given
		UserResponseDTO response = UserResponseDTO.builder()
			.name("heehee")
			.age(10)
			.hobby("coding")
			.createdAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.modifiedAt(LocalDateTime.of(2000, 1, 1, 1, 1))
			.build();

		when(userService.createUser(any(CreateUserRequestDto.class))).thenReturn(response);

		// when
		CreateUserRequestDto requestDto = new CreateUserRequestDto("heehee", 10, "coding");

		ResultActions result = this.mockMvc.perform(
			post("/users")
				.contentType((MediaType.APPLICATION_JSON))
				.content(objectMapper.writeValueAsString(requestDto))
		);

		// then
		result.andExpect(status().isOk())
			.andDo(print())
			.andDo(document("member-save",
					requestFields(
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
					),
					responseFields(
						fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
						fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
						fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 시간"),
						fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("수정 시간")
					)
				)
			);
	}
}