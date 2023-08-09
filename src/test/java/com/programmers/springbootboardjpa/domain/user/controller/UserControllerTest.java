package com.programmers.springbootboardjpa.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.domain.user.dto.UserRequestDto;
import com.programmers.springbootboardjpa.domain.user.dto.UserResponseDto;
import com.programmers.springbootboardjpa.domain.user.service.UserService;
import com.programmers.springbootboardjpa.global.error.ErrorCode;
import com.programmers.springbootboardjpa.global.error.exception.InvalidEntityValueException;
import com.programmers.springbootboardjpa.global.error.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureRestDocs
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @DisplayName("회원 등록 성공")
    @Test
    void create() throws Exception {
        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("김이름")
                .age(26)
                .hobby("산책")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("김이름")
                .age(26)
                .hobby("산책")
                .createdAt(LocalDateTime.now())
                .createdBy(null)
                .build();

        when(userService.create(any(UserRequestDto.class))).thenReturn(userResponseDto);

        //when
        //then
        mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponseDto.id()))
                .andExpect(jsonPath("$.name").value("김이름"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.hobby").value("산책"))
                .andDo(document("user-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 등록 실패")
    @Test
    void createFail() throws Exception {
        //given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("1111")
                .age(26)
                .hobby("산책")
                .build();

        when(userService.create(any(UserRequestDto.class))).thenThrow(new InvalidEntityValueException(ErrorCode.INVALID_USER_NAME_PATTERN));

        //when
        //then
        mockMvc.perform(post("/api/v1/users")
                        .content(objectMapper.writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value("INVALID_USER_NAME_PATTERN"))
                .andExpect(jsonPath("$.message").value("회원 이름은 한글 또는 영어로 입력해주세요."))
                .andDo(document("user-create-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ), responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }

    @DisplayName("회원 페이징 조회 성공")
    @Test
    void findAll() throws Exception {
        //given
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("김이름")
                .age(26)
                .hobby("산책")
                .createdAt(LocalDateTime.now())
                .createdBy(null)
                .build();

        List<UserResponseDto> userResponseDtos = List.of(userResponseDto);
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<UserResponseDto> userResponseDtoPage = new PageImpl<>(userResponseDtos, pageRequest, userResponseDtos.size());
        when(userService.findAll(any(Pageable.class))).thenReturn(userResponseDtoPage);

        //when
        //then
        mockMvc.perform(get("/api/v1/users")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(userResponseDto.id()))
                .andExpect(jsonPath("$.content[0].name").value("김이름"))
                .andExpect(jsonPath("$.content[0].age").value(26))
                .andExpect(jsonPath("$.content[0].hobby").value("산책"))
                .andDo(document("user-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("content[].name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("content[].age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("content[].hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("content[].createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty").ignored(),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted").ignored(),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted").ignored(),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset").ignored(),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged").ignored(),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged").ignored(),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last").ignored(),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("size").ignored(),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("number").ignored(),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty").ignored(),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted").ignored(),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted").ignored(),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("first").ignored(),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements").ignored(),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("empty").ignored()
                        )
                ));
    }

    @DisplayName("회원 단건 조회 성공")
    @Test
    void findById() throws Exception {
        //given
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("김이름")
                .age(26)
                .hobby("산책")
                .createdAt(LocalDateTime.now())
                .createdBy(null)
                .build();

        when(userService.findById(any(Long.class))).thenReturn(userResponseDto);

        //when
        //then
        mockMvc.perform(get("/api/v1/users/{id}", userResponseDto.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponseDto.id()))
                .andExpect(jsonPath("$.name").value("김이름"))
                .andExpect(jsonPath("$.age").value(26))
                .andExpect(jsonPath("$.hobby").value("산책"))
                .andDo(document("user-get-one",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 단건 조회 실패")
    @Test
    void findByIdFail() throws Exception {
        //given
        when(userService.findById(any(Long.class))).thenThrow(new NotFoundException(ErrorCode.USER_NOT_FOUND));

        //when
        //then
        mockMvc.perform(get("/api/v1/users/{id}", 11111L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("NOT_FOUND"))
                .andExpect(jsonPath("$.code").value("USER_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("회원을 찾을 수 없습니다."))
                .andDo(document("user-get-one-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }

    @DisplayName("회원 수정 성공")
    @Test
    void update() throws Exception {
        //given
        UserRequestDto userUpdateRequestDto = UserRequestDto.builder()
                .name("이이름")
                .age(28)
                .hobby("영화 보기")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name("이이름")
                .age(28)
                .hobby("영화 보기")
                .createdAt(LocalDateTime.now())
                .createdBy(null)
                .build();

        when(userService.update(any(Long.class), any(UserRequestDto.class))).thenReturn(userResponseDto);

        //when
        //then
        mockMvc.perform(put("/api/v1/users/{id}", userResponseDto.id())
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(userResponseDto.id()))
                .andExpect(jsonPath("$.name").value("이이름"))
                .andExpect(jsonPath("$.age").value(28))
                .andExpect(jsonPath("$.hobby").value("영화 보기"))
                .andDo(document("user-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("createdBy").type(JsonFieldType.NULL).description("createdBy"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt")
                        )
                ));
    }

    @DisplayName("회원 수정 실패")
    @Test
    void updateFail() throws Exception {
        //given
        UserRequestDto userUpdateRequestDto = UserRequestDto.builder()
                .name(" ")
                .age(28)
                .hobby("영화 보기")
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1L)
                .name(" ")
                .age(28)
                .hobby("영화 보기")
                .createdAt(LocalDateTime.now())
                .createdBy(null)
                .build();

        Long userId = userResponseDto.id();

        when(userService.update(any(Long.class), any(UserRequestDto.class))).thenReturn(userResponseDto);

        //when
        //then
        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .content(objectMapper.writeValueAsString(userUpdateRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value("INVALID_ENTITY_VALUE"))
                .andExpect(jsonPath("$.message").value("이름을 입력해주세요."))
                .andDo(document("user-update-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("timestamp").type(JsonFieldType.STRING).description("timestamp"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("error").type(JsonFieldType.STRING).description("error"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("code"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message")
                        )
                ));
    }
}