package com.example.board.controller;

import com.example.board.dto.PostDto;
import com.example.board.dto.PostUpdateDto;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostControllerTest {

    @Autowired
    PostController postController;

    @Autowired
    PostService postService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("정상적으로 게시글을 등록된다.")
    @Transactional
    void postSuccess() throws Exception {
        //given
        PostDto postDto = new PostDto(1L, "test2", "test Contents2");

        //when, then
        mockMvc.perform(post("http://localhost:8080/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postDto)))
                .andExpect(status().isOk())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("contents")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("isSuccess").type(JsonFieldType.STRING).description("isSuccess")
                        )
                ));
    }

    @Test
    @DisplayName("정상적으로 모든 게시물들을 받아온다.")
    @Transactional
    void getSuccess() throws Exception {
        //given
        postService.save(new PostDto(1L, "testTitle1", "hihihihihihihih1"));
        postService.save(new PostDto(1L, "testTitle2", "hihihihihihihih2"));

        //when, then
        mockMvc.perform(get("http://localhost:8080/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("contents-list"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("contents-list"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.content[].userName").type(JsonFieldType.STRING).description("username"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.content[].userName").type(JsonFieldType.STRING).description("username"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("unpaged"),
                                fieldWithPath("isSuccess").type(JsonFieldType.STRING).description("isSuccess")
                        )
                ));
    }

    @Test
    @DisplayName("정상적으로 특정 게시물 하나를 받아온다..")
    @Transactional
    void getPostByIdSuccess() throws Exception {
        //given
        Long savedContentsId = postService.save(new PostDto(1L, "testTitle", "hihihihihihihih"));

        //when, then
        mockMvc.perform(get("http://localhost:8080/api/v1/posts/" + savedContentsId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("post-getById",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("contents-list"),
                                fieldWithPath("data.userName").type(JsonFieldType.STRING).description("username"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.contents").type(JsonFieldType.STRING).description("contents"),
                                fieldWithPath("isSuccess").type(JsonFieldType.STRING).description("isSuccess")
                        )
                ));
    }

    @Test
    @DisplayName("정상적으로 게시글을 수정한다.")
    @Transactional
    void updatePostSuccess() throws Exception {
        //given
        Long savedContents = postService.save(new PostDto(1L, "testTitle", "hihihihihihihih"));

        PostUpdateDto postUpdateDto = new PostUpdateDto(1L, "test2", "test Contents2");

        //when, then
        mockMvc.perform(patch("http://localhost:8080/api/v1/posts/" + savedContents)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title update"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("contents update")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("isSuccess").type(JsonFieldType.STRING).description("isSuccess")
                        )
                ));
    }

}