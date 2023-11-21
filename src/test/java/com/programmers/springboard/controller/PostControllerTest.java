package com.programmers.springboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springboard.entity.Member;
import com.programmers.springboard.entity.Post;
import com.programmers.springboard.repository.MemberRepository;
import com.programmers.springboard.request.CreatePostRequest;
import com.programmers.springboard.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void set_up() {
        Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());

        CreatePostRequest request = new CreatePostRequest("제목입니다", "내용입니다", member.getId());


    }

    @Test
    void 포스트_작성_성공() throws Exception {
        // given
        Member member = memberRepository.save(Member.builder().name("홍길동").age(20).hobby("축구").build());
        CreatePostRequest request = new CreatePostRequest("test", "test", member.getId());

        // when // then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
                        )
                ));
    }

    @Test
    void 포스트_페이징_조회() {
        // given

        // when // then
        mockMvc.perform()
    }

}
