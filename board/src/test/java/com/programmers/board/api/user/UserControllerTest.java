package com.programmers.board.api.user;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.board.core.user.application.UserService;
import com.programmers.board.core.user.application.dto.UserCreateRequest;
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

@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureRestDocs
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  @DisplayName("Happy Path: 유저 회원 가입 테스트")
  void joinUser() throws Exception {

    UserCreateRequest userCreateRequest = UserCreateRequest.builder()
        .name("aaa")
        .age(22)
        .hobby("COOKING")
        .build();

    mockMvc.perform(post("/api/v1/users/join")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(userCreateRequest))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("user-join",
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
            ),
            responseFields(
                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태 코드"),
                fieldWithPath("body").type(JsonFieldType.OBJECT).description("body"),
                fieldWithPath("body.id").type(JsonFieldType.NUMBER).description("아이디"),
                fieldWithPath("body.name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("body.age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("body.hobby").type(JsonFieldType.STRING).description("취미")
            )
        ));
  }
}