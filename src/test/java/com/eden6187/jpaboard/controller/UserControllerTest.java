package com.eden6187.jpaboard.controller;

import com.eden6187.jpaboard.test_data.UserMockData;
import com.eden6187.jpaboard.controller.UserController.AddUserRequestDto;
import com.eden6187.jpaboard.exception.DuplicatedUserNameException;
import com.eden6187.jpaboard.repository.UserRepository;
import com.eden6187.jpaboard.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
    UserController.class
})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @MockBean
  UserService userService;

  @MockBean
  UserRepository userRepository;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void addUserTest() throws Exception {
    AddUserRequestDto saveUserRequestDto = AddUserRequestDto.builder()
        .age(UserMockData.TEST_AGE)
        .name(UserMockData.TEST_NAME)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    when(userService.addUser(saveUserRequestDto)).thenReturn(UserMockData.TEST_ID);

    mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveUserRequestDto))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("user-add",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status_code"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING)
                        .description("server_datetime"),
                    fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("user_id")
                )
            )
        );
  }

  @Test
  void handleDuplicatedUserNameException() throws Exception{
    AddUserRequestDto saveUserRequestDto = AddUserRequestDto.builder()
        .age(UserMockData.TEST_AGE)
        .name(UserMockData.TEST_NAME)
        .hobby(UserMockData.TEST_HOBBY)
        .build();

    when(userService.addUser(any())).thenThrow(new DuplicatedUserNameException());

    mockMvc.perform(post("/api/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(saveUserRequestDto))
        )
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andDo(document("user-duplicated",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("server_datetime"),
                    fieldWithPath("errorCode.statusCode").type(JsonFieldType.NUMBER).description("status_code"),
                    fieldWithPath("errorCode.privateCode").type(JsonFieldType.STRING).description("private_code"),
                    fieldWithPath("errorCode.message").type(JsonFieldType.STRING).description("message")
                )
            )
        );

  }

}