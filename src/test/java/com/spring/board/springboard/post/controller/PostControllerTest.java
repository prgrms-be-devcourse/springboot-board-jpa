package com.spring.board.springboard.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.board.springboard.post.domain.dto.RequestPostDto;
import com.spring.board.springboard.post.service.PostService;
import com.spring.board.springboard.user.domain.Hobby;
import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeAll
    void setUp() {
        memberRepository.save(
                new Member("이수린", 24, Hobby.SLEEP)
        );

        RequestPostDto requestPostDTO = new RequestPostDto(
                "스프링 게시판 미션",
                "이 미션 끝나면 크리스마스에요",
                1);

        postService.createPost(requestPostDTO);
    }

    @DisplayName("모든 게시물을 불러올 수 있다.")
    @Test
    void getAllPosts() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page",
                                String.valueOf(0))
                        .param("size",
                                String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-post",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("post array"),
                                fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("[].member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("[].member.name").type(JsonFieldType.STRING).description("member name"),
                                fieldWithPath("[].member.age").type(JsonFieldType.NUMBER).description("member age"),
                                fieldWithPath("[].member.hobby").type(JsonFieldType.STRING).description("member hobby")
                        ))
                );
    }

    @DisplayName("게시물을 새로 생성할 수 있다.")
    @Test
    void createPost() throws Exception {
        // given
        RequestPostDto requestPostDTO = new RequestPostDto(
                "저장하기",
                "게시판에 새 글 저장하기",
                1);

        // when and then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("writer member id")
                        ))
                );
    }

    @DisplayName("하나의 게시물을 조회할 수 있다.")
    @Test
    void getPost() throws Exception {
        mockMvc.perform(get("/posts/{postId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-one-post",
                        pathParameters(
                                parameterWithName("postId").description("post id")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("member name"),
                                fieldWithPath("member.age").type(JsonFieldType.NUMBER).description("member age"),
                                fieldWithPath("member.hobby").type(JsonFieldType.STRING).description("member hobby")
                        ))
                );
    }

    @DisplayName("게시물의 제목과 내용을 수정할 수 있다.")
    @Test
    void updatePost() throws Exception {
        RequestPostDto updatePostDTO = new RequestPostDto(
                "수정하기",
                "게시판 새 글 저장하기 -> 수정된 내용임",
                1
        );

        mockMvc.perform(post("/posts/{postId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("writer member id")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("post id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("created at"),
                                fieldWithPath("member").type(JsonFieldType.OBJECT).description("member"),
                                fieldWithPath("member.name").type(JsonFieldType.STRING).description("member name"),
                                fieldWithPath("member.age").type(JsonFieldType.NUMBER).description("member age"),
                                fieldWithPath("member.hobby").type(JsonFieldType.STRING).description("member hobby")
                        ))
                );
    }
}