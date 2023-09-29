package com.blackdog.springbootBoardJpa.user.controller;

import com.blackdog.springbootBoardJpa.domain.user.controller.UserController;
import com.blackdog.springbootBoardJpa.domain.user.controller.converter.UserControllerConverter;
import com.blackdog.springbootBoardJpa.domain.user.controller.dto.UserCreateDto;
import com.blackdog.springbootBoardJpa.domain.user.service.UserService;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponse;
import com.blackdog.springbootBoardJpa.domain.user.service.dto.UserResponses;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserControllerConverter controllerConverter;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저를 생성한다.")
    void saveUser_Dto_ReturnResponse() throws Exception {
        //given
        UserCreateDto createDto = new UserCreateDto("Kim", 23, "축구");
        UserResponse response = new UserResponse(1L, "Kim", 23, "축구", LocalDateTime.now(), LocalDateTime.now());
        given(userService.saveUser(any())).willReturn(response);

        //when & then
        mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 ID"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("회원 가입 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("회원 수정 시간")
                        )
                ));
    }

    @Test
    @DisplayName("유효하지 않은 유저는 생성 못한다.")
    void saveUser_Dto_ReturnFailResponse() throws Exception {
        //given
        UserCreateDto createDto = new UserCreateDto("", -1, "");
        UserResponse response = new UserResponse(1L, "", -1, "", LocalDateTime.now(), LocalDateTime.now());
        given(userService.saveUser(any())).willReturn(response);

        //when & then
        mockMvc.perform(post("/users")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    @DisplayName("유저를 삭제한다.")
    void deleteUser_Id_ReturnMessage() throws Exception {
        //given
        doNothing().when(userService).deleteUserById(anyLong());

        //when & then
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/users/{userId}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-delete",
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        )));
    }

    @Test
    @DisplayName("유저를 단건 조회한다.")
    void getUser_Id_ReturnResponse() throws Exception {
        //given
        UserResponse response = new UserResponse(1L, "Park", 26, "여행", LocalDateTime.now(), LocalDateTime.now());
        given(userService.findUserById(anyLong())).willReturn(response);

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/users/{userId}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-get",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("회원 ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("유저 생성일"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("유저 갱신일")
                        )
                ));

        // then
        verify(userService, times(1)).findUserById(anyLong());
    }

    @Test
    @DisplayName("유저를 전체 조회한다.")
    void getAllUsers_Pageable_ReturnResponses() throws Exception {
        //given
        UserResponse response1 = new UserResponse(1L, "Park", 26, "여행", LocalDateTime.now(), LocalDateTime.now());
        UserResponse response2 = new UserResponse(2L, "Park", 26, "여행", LocalDateTime.now(), LocalDateTime.now());
        UserResponses responses = new UserResponses(new PageImpl<>(List.of(response1, response2)));
        given(userService.findAllUsers(PageRequest.of(0, 5))).willReturn(responses);

        //when & then
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userResponses.content", Matchers.hasSize(2)))
                .andDo(print())
                .andDo(document("users-get",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈")
                        ),
                        responseFields(
                                fieldWithPath("userResponses").type(JsonFieldType.OBJECT).description("유저 응답"),
                                fieldWithPath("userResponses.content[]").type(JsonFieldType.ARRAY).description("유저 정보 배열"),
                                fieldWithPath("userResponses.content[].id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userResponses.content[].name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userResponses.content[].age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("userResponses.content[].hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("userResponses.content[].createdAt").type(JsonFieldType.STRING).description("유저 생성일"),
                                fieldWithPath("userResponses.content[].updatedAt").type(JsonFieldType.STRING).description("유저 갱신일"),
                                fieldWithPath("userResponses.pageable").type(JsonFieldType.OBJECT).description("pageable").ignored(),
                                fieldWithPath("userResponses.last").type(JsonFieldType.BOOLEAN).description("last").ignored(),
                                fieldWithPath("userResponses.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("userResponses.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("userResponses.size").type(JsonFieldType.NUMBER).description("size").ignored(),
                                fieldWithPath("userResponses.number").type(JsonFieldType.NUMBER).description("number").ignored(),
                                fieldWithPath("userResponses.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty").ignored(),
                                fieldWithPath("userResponses.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted").ignored(),
                                fieldWithPath("userResponses.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted").ignored(),
                                fieldWithPath("userResponses.first").type(JsonFieldType.BOOLEAN).description("first").ignored(),
                                fieldWithPath("userResponses.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements").ignored(),
                                fieldWithPath("userResponses.empty").type(JsonFieldType.BOOLEAN).description("empty").ignored()
                        )
                ));
    }

}
