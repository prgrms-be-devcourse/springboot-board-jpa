package com.kdt.springbootboardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.post.service.PostService;
import com.kdt.springbootboardjpa.post.service.dto.CreatePostRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.springbootboardjpa.member.domain.Hobby.GAME;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@AutoConfigureRestDocs
@AutoConfigureMockMvc   // rest 호출 쉽게 가능
@Transactional
@SpringBootTest
class PostControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    Long memberId;
    Long postId;

    @BeforeEach
    void createdPost() {
        Member member = Member.builder()
                .name("최은비")
                .age(25)
                .hobby(GAME)
                .build();

        memberId = memberRepository.save(member).getId();

        CreatePostRequest postDto = CreatePostRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .memberId(memberId)
                .build();

        postId = postService.save(postDto).getId();
    }

    @Test
    void saveCallTest() throws Exception {
        //given
        CreatePostRequest postDto = CreatePostRequest.builder()
                .title("테스트 제목")
                .content("테스트 내용")
                .memberId(memberId)
                .build();

        //when  //then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
                        )
                ));
    }

    @Test
    void findByIdCallTest() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()) // 쓸데없는 IO 출력이 있을 수 있으므로 삭제하는 것이 좋음
                .andDo(document("post-findById",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("memberName").type(JsonFieldType.STRING).description("memberName")
                        )));
    }

    @Test
    void findAllCallTest() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("postId"),
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("[].memberName").type(JsonFieldType.STRING).description("memberName")
                        )));
    }
}