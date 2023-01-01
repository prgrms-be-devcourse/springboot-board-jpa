package com.programmers.jpaboard.web.user;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpaboard.domain.user.dto.UserCreateRequestDto;
import com.programmers.jpaboard.domain.user.dto.UserResponseDto;
import com.programmers.jpaboard.domain.user.service.UserService;

@WebMvcTest(UserApiController.class)
@ExtendWith(RestDocumentationExtension.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class UserApiControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserService userService;

	private UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto("권성준", "google@gmail.com", 26, "달리기");
	private UserResponseDto userResponseDto = new UserResponseDto(
		1L,
		userCreateRequestDto.getName(),
		userCreateRequestDto.getEmail(),
		userCreateRequestDto.getAge(),
		userCreateRequestDto.getHobby(),
		Collections.emptyList()
	);

	@Test
	void createTest() throws Exception {

		String requestBody = objectMapper.writeValueAsString(userCreateRequestDto);
		when(userService.createUser(any(UserCreateRequestDto.class))).thenReturn(userResponseDto);

		mockMvc.perform(post("/api/v1/users")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(requestBody))
			.andExpect(header().string("Location", "/api/v1/users/" + userResponseDto.getId()))
			.andExpect(status().isCreated())
			.andDo(print())
			.andDo(document("user-save",
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
					fieldWithPath("age").type(JsonFieldType.NUMBER).description("사용자 나이"),
					fieldWithPath("hobby").type(JsonFieldType.STRING).description("사용자 취미")
				),
				responseHeaders(
					headerWithName("Location").description("생성된 사용자 URI")
				)));

		verify(userService).createUser(any(UserCreateRequestDto.class));
	}
}
