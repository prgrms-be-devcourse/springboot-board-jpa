package com.kdt.jpaboard.domain.board.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.jpaboard.domain.board.user.dto.CreateUserDto;
import com.kdt.jpaboard.domain.board.user.dto.UserDto;
import com.kdt.jpaboard.domain.board.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@DisplayName("사용자 controller 테스트")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    private CreateUserDto userDto;

    @BeforeEach
    void setup() {
        userDto = CreateUserDto.builder()
                .name("beomsic")
                .age(26)
                .hobby("soccer")
                .build();
    }

    @AfterEach
    void reset() {
        userService.deleteAll();
    }

    @Test
    @DisplayName("저장 테스트")
    void save() throws Exception {
        // Given
        PageRequest page = PageRequest.of(0, 10);

        // When
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-save",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields (
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        // Then
        Page<UserDto> all = userService.findAll(page);
        assertThat(all.getTotalElements() == 1, is(true));
    }

    @Test
    @DisplayName("모든 사용자 조회 테스트")
    void findAll() throws Exception {
        /// Given

        // When
        userService.save(userDto);
        mockMvc.perform(get("/users")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "createdAt,DESC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("serverDateTime").exists())
                .andDo(print())
                .andDo(document("user-find-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("사용자 리스트"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("사용자 아이디"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("취미"),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),

                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),

                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),

                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        // Then
    }

    @Test
    @DisplayName("특정 사용자 조회 테스트")
    void findOne() throws Exception {
        // Given

        // When
        Long userId = userService.save(userDto);
        mockMvc.perform(get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-find-by-id",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("사용자 아이디"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        // Then
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void testUpdate() throws Exception {
        // Given
        CreateUserDto updateUserDto = new CreateUserDto(userDto.getName(), 20, "sleep");

        // When
        Long userId = userService.save(userDto);
        mockMvc.perform(put("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateUserDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-update",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields (
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

        // Then
        UserDto one = userService.findById(userId);
        assertThat(one.getHobby().equals(updateUserDto.getHobby()), is(true));
        assertThat(one.getId().equals(userId), is(true));
    }

}