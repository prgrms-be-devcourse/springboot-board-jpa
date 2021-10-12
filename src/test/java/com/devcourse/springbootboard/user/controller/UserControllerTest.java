package com.devcourse.springbootboard.user.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.dto.UserDeleteResponse;
import com.devcourse.springbootboard.user.dto.UserResponse;
import com.devcourse.springbootboard.user.dto.UserSignUpRequest;
import com.devcourse.springbootboard.user.dto.UserUpdateRequest;
import com.devcourse.springbootboard.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	@DisplayName("회원가입 요청 테스트")
	@Test
	void testCreateUser() throws Exception {
		// given
		given(userService.saveUser(any(UserSignUpRequest.class))).willReturn(new UserResponse(
			1L,
			"명환",
			29,
			new Hobby("풋살")
		));

		// when
		UserSignUpRequest userSignUpRequest = new UserSignUpRequest(
			"명환",
			29,
			"풋살"
		);
		ResultActions result = mockMvc.perform(
			post("/users")
				.content(objectMapper.writeValueAsString(userSignUpRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"create-user",
					requestFields(
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
					),
					responseFields(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby.hobby").type(JsonFieldType.STRING).description("취미")
					)
				));
	}

	@DisplayName("회원 정보 조회 요청 테스트")
	@Test
	void testGetUser() throws Exception {
		// given
		given(userService.findUser(anyLong())).willReturn(new UserResponse(1L, "명환", 29, new Hobby("풋살")));

		// when
		ResultActions result = mockMvc.perform(
			get("/users/{id}", 1)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"get-user",
					pathParameters(
						parameterWithName("id").description("유저 아이디")
					),
					responseFields(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby.hobby").type(JsonFieldType.STRING).description("취미")
					)
				));
	}

	@DisplayName("회원 정보 수정 요청 테스트")
	@Test
	void testModifyUser() throws Exception {
		// given
		given(userService.updateUser(anyLong(), any(UserUpdateRequest.class))).willReturn(new UserResponse(
			1L,
			"Sam",
			29,
			new Hobby("축구")
		));

		// when
		UserUpdateRequest userUpdateRequest = new UserUpdateRequest(
			"Sam",
			29,
			"축구"
		);
		ResultActions result = mockMvc.perform(
			put("/users/{id}/profile", 1)
				.content(objectMapper.writeValueAsString(userUpdateRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"modify-user",
					requestFields(
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
					),
					responseFields(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
						fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
						fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
						fieldWithPath("hobby.hobby").type(JsonFieldType.STRING).description("취미")
					)
				));
	}

	@DisplayName("회원 탈퇴 요청 테스트")
	@Test
	void testRemoveUser() throws Exception {
		// given
		given(userService.deleteUser(anyLong())).willReturn(new UserDeleteResponse(1L));

		// when
		ResultActions result = mockMvc.perform(
			delete("/users/{id}", 1)
				.accept(MediaType.APPLICATION_JSON));

		// then
		result.andExpect(status().isOk())
			.andDo(
				document(
					"remove-user",
					pathParameters(
						parameterWithName("id").description("유저 아이디")
					),
					responseFields(
						fieldWithPath("id").type(JsonFieldType.NUMBER).description("삭제된 유저 아이디")
					)
				));
	}
}
