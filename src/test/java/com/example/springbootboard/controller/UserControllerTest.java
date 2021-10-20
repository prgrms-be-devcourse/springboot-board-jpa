package com.example.springbootboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.service.PostService;
import com.example.springbootboard.service.UserService;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Long id;

    @BeforeEach
    void setUp() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("testName")
                .age(26)
                .hobby("testHobby")
                .build();

        id = userService.insert(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .userId(id)
                .title("testTitle")
                .content("testContent")
                .build();

        postService.insert(postRequestDto);
    }

    @AfterEach
    void cleanUp() {
        userService.deleteAll();
    }

    @Test
    void insertTest() throws Exception {
        // given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .name("testName")
                .age(26)
                .hobby("testHobby")
                .build();
        // when  // then
        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-insert",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));

    }

    @Test
    void getOneTest() throws Exception {
        mockMvc.perform(get("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-getOne",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.postDtos").type(JsonFieldType.ARRAY).description("유저 게시글"),
                                fieldWithPath("data.postDtos[].userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.postDtos[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.postDtos[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.postDtos[].content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-getAll",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("유저 name"),
                                fieldWithPath("data[].age").type(JsonFieldType.NUMBER).description("유저 age"),
                                fieldWithPath("data[].hobby").type(JsonFieldType.STRING).description("유저 hobby"),
                                fieldWithPath("data[].postDtos").type(JsonFieldType.ARRAY).description("유저의 게시글"),
                                fieldWithPath("data[].postDtos[].userId").type(JsonFieldType.NUMBER).description("유저 userId"),
                                fieldWithPath("data[].postDtos[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data[].postDtos[].title").type(JsonFieldType.STRING).description("게시글 title"),
                                fieldWithPath("data[].postDtos[].content").type(JsonFieldType.STRING).description("게시글 content"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void updateTest() throws Exception {
        UserRequestDto userRequestDtoForUpdate = UserRequestDto.builder()
                .name("updatedName")
                .age(26)
                .hobby("updatedHobby")
                .build();

        mockMvc.perform(patch("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDtoForUpdate)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-update",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("유저 name"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("유저 age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("유저 hobby"),
                                fieldWithPath("data.postDtos").type(JsonFieldType.ARRAY).description("유저의 게시글"),
                                fieldWithPath("data.postDtos[].userId").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.postDtos[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.postDtos[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.postDtos[].content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    void deleteTest() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-delete",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
                        )
                ));
    }

    @Test
    @DisplayName("유저 생성 요청은 이름과 나이가 필수여야 한다.")
    void userRequestArgumentTest() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                        .build();

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequestDto)))
                .andDo(print());
    }
}