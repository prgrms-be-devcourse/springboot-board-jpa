package com.programmers.iyj.springbootboard.domain.post.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.post.service.PostService;
import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(PostApi.class)
class PostApiTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private static PostDto postDto;

    @BeforeAll
    public static void setUp() {
        postDto = PostDto.builder()
                .id(1L)
                .title("title1")
                .content("content1")
                .userDto(
                        UserDto.builder()
                                .id(1L)
                                .name("john")
                                .age(25)
                                .hobby(Hobby.NETFLIX)
                                .build()
                )
                .build();
    }

    @Test
    @DisplayName("getOne api should response OK")
    public void getOne() throws Exception {
        // Given
        given(postService.findOneById(anyLong())).willReturn(postDto);

        // When
        ResultActions actions = mockMvc.perform(get("/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data.title").value("title1"))
                .andExpect(jsonPath("data.content").value("content1"));

        actions.andExpect(status().isOk())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 시간"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        )));
    }

    @Test
    @DisplayName("getAll api should response OK")
    void getAll() throws Exception {
        // Given
        List<PostDto> postDtos = new ArrayList<>();
        postDtos.add(postDto);
        Page<PostDto> postDtoPage = new PageImpl<>(postDtos);

        given(postService.findAll(any())).willReturn(postDtoPage);

        // When
        ResultActions actions = mockMvc.perform(get("/posts")
                .contentType(MediaType.APPLICATION_JSON))
                        .andDo(print());

        // Then
        actions.andExpect(status().isOk())
                .andDo(document("posts-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 시간"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        )));
    }

    @Test
    @DisplayName("save api should response OK")
    public void save() throws Exception {
        // Given
        given(postService.save(any())).willReturn(postDto);

        // When
        ResultActions actions = mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data.title").value("title1"))
                .andExpect(jsonPath("data.content").value("content1"));

        actions.andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 시간"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        )));
    }

    @Test
    @DisplayName("update api should response OK")
    public void update() throws Exception {
        // Given
        given(postService.save(any(), anyLong())).willReturn(postDto);

        // When
        ResultActions actions = mockMvc.perform(patch("/posts/{id}", 1L)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        // Then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data.title").value("title1"))
                .andExpect(jsonPath("data.content").value("content1"));

        actions.andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.STRING).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 시간"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시물 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 아이디"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("취미")
                        )));
    }
}