package com.programmers.springbootboardjpa.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private UserRequestDto userRequestDto;

    @BeforeEach
    void setUp() {
        userRequestDto = UserRequestDto.builder()
                .name("김이름")
                .age(26)
                .hobby("산책")
                .build();
    }

    @DisplayName("회원 등록 성공")
    @Test
    void create() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 등록 실패")
    @Test
    void createFail() throws Exception {
        //given
        userRequestDto = UserRequestDto.builder()
                .name("1111")
                .age(26)
                .hobby("산책")
                .build();

        //when
        //then
        mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-create-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }

    @DisplayName("회원 페이징 조회 성공")
    @Test
    void findAll() throws Exception {
        //given
        userService.create(userRequestDto);

        //when
        //then
        mockMvc.perform(get("/api/v1/users")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted").ignored(),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset").ignored(),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged").ignored(),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged").ignored(),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last").ignored(),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size").ignored(),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number").ignored(),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty").ignored(),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted").ignored(),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted").ignored(),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first").ignored(),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements").ignored(),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty").ignored()
                        )
                ));
    }

    @DisplayName("회원 단건 조회 성공")
    @Test
    void findById() throws Exception {
        //given
        UserResponseDto userResponseDto = userService.create(userRequestDto);
        Long savedUserId = userResponseDto.id();

        //when
        //then
        mockMvc.perform(get("/api/v1/users/{id}", savedUserId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-get-one",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 단건 조회 실패")
    @Test
    void findByIdFail() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/v1/users/{id}", 11111L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-get-one-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }

    @DisplayName("회원 수정 성공")
    @Test
    void update() throws Exception {
        //given
        UserResponseDto userResponseDto = userService.create(userRequestDto);
        Long savedUserId = userResponseDto.id();

        UserRequestDto userUpdateRequestDto = UserRequestDto.builder()
                .name("이이름")
                .age(28)
                .hobby("영화 보기")
                .build();

        //when
        //then
        mockMvc.perform(put("/api/v1/users/{id}", savedUserId)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 수정 실패")
    @Test
    void updateFail() throws Exception {
        //given
        UserResponseDto userResponseDto = userService.create(userRequestDto);
        Long savedUserId = userResponseDto.id();

        UserRequestDto userUpdateRequestDto = UserRequestDto.builder()
                .name("123")
                .age(28)
                .hobby("영화 보기")
                .build();

        //when
        //then
        mockMvc.perform(put("/api/v1/users/{id}", savedUserId)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-update-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("httpStatus").type(JsonFieldType.STRING).description("httpStatus"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data")
                        )
                ));
    }
}