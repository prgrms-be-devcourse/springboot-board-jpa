package com.devcourse.springjpaboard.user.controller;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_HOBBY;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.BLANK_NAME;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.INVALID_RANGE_AGE;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springjpaboard.application.user.controller.UserController;
import com.devcourse.springjpaboard.application.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.application.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.application.user.service.UserServiceImpl;
import com.devcourse.springjpaboard.core.exception.GlobalExceptionHandler;
import com.devcourse.springjpaboard.core.exception.NotFoundException;
import com.devcourse.springjpaboard.user.controller.stub.UserStubs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class UserControllerTest {

  @InjectMocks
  private UserController userController;

  @Mock
  private UserServiceImpl userService;

  private MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void init(RestDocumentationContextProvider restDocumentationContextProvider) {
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .setControllerAdvice(new GlobalExceptionHandler())
        .build();
  }

  @Test
  @DisplayName("회원가입 테스트")
  void createUserTest() throws Exception {
    // given
    CreateUserRequest request = UserStubs.createUserRequest();
    UserResponse response = UserStubs.userResponse();

    doReturn(response)
        .when(userService)
        .save(any(CreateUserRequest.class));

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    );

    // then
    resultActions.andExpect(status().isOk())
        .andDo(document("user-save",
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("String"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("int"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("String")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @ParameterizedTest
  @MethodSource("com.devcourse.springjpaboard.user.controller.stub.UserStubs#blankNameUserRequest")
  @DisplayName("유저 이름이 입력되지 않은 상태로 생성 요청시 예외 발생")
  void createUserBlankNameTest(String name, int age, String hobby) throws Exception {
    // given
    CreateUserRequest blankNameRequest = new CreateUserRequest(
        name,
        age,
        hobby
    );

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(blankNameRequest))
    );

    // then
    resultActions.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.data.message").value(BLANK_NAME))
        .andExpect(jsonPath("$.data.input").value(name));
  }

  @ParameterizedTest
  @MethodSource("com.devcourse.springjpaboard.user.controller.stub.UserStubs#notValidAgeUserRequest")
  @DisplayName("잘못된 나이로 생성 요청시 예외 발생")
  void createUserNotValidAgeTest(String name, int age, String hobby) throws Exception {
    // given
    CreateUserRequest notValidAgeRequest = new CreateUserRequest(
        name,
        age,
        hobby
    );

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(notValidAgeRequest))
    );

    // then
    resultActions.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.data.message").value(INVALID_RANGE_AGE))
        .andExpect(jsonPath("$.data.input").value(age));
  }

  @ParameterizedTest
  @MethodSource("com.devcourse.springjpaboard.user.controller.stub.UserStubs#blankHobbyUserRequest")
  @DisplayName("취미를 입력하지 않았을 경우 예외 발생")
  void createUserBlankHobbyTest(String name, int age, String hobby) throws Exception {
    // given
    CreateUserRequest blankHobbyRequest = new CreateUserRequest(
        name,
        age,
        hobby
    );

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(blankHobbyRequest))
    );

    // then
    resultActions.andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.data.message").value(BLANK_HOBBY))
        .andExpect(jsonPath("$.data.input").value(hobby));
  }

  @DisplayName("존재하는 회원 ID로 조회 테스트")
  void getUserByIdTest() throws Exception {
    // given
    Long request = 1L;
    UserResponse response = UserStubs.userResponse();

    doReturn(response).when(userService).findById(request);
    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/users/1")
    );
    // then
    resultActions.andExpect(status().isOk())
        .andDo(document("user-find-by-id",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("존재하지 않는 회원 ID로 조회시 예외 발생 테스트")
  void getUserByIdFailTest() throws Exception {
    // given
    Long request = 2L;
    doThrow(new NotFoundException(NOT_FOUND_USER)).when(userService).findById(request);

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/users/2")
    );
    // then
    MvcResult mvcResult = resultActions
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("data").exists())
        .andReturn();
  }
}