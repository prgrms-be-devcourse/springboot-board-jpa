package com.example.board.domain.post.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.service.MemberService;
import com.example.board.domain.post.dto.PostRequest;
import com.example.board.domain.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberService memberService;

  @Autowired
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void newPost() throws Exception {
    //given
    MemberRequest memberRequest = new MemberRequest("김환", 25, "게임");
    Long savedMemberId = memberService.save(memberRequest);
    PostRequest postRequest = new PostRequest("제목", "내용", savedMemberId);

    //when & then
    mockMvc.perform(post("/post")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("post-save",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("member id")
            )));
  }

  @Test
  void findPostById() throws Exception {
    //given
    MemberRequest memberRequest = new MemberRequest("김환", 25, "게임");
    Long savedMemberId = memberService.save(memberRequest);
    PostRequest postRequest = new PostRequest("제목", "내용", savedMemberId);
    Long savedPostId = postService.save(postRequest);

    //when & then
    mockMvc.perform(get("/post/" + savedPostId))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-find-by-id",
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("post id"),
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("author").type(JsonFieldType.OBJECT).description("member who post this"),
                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("member id"),
                fieldWithPath("author.name").type(JsonFieldType.STRING).description("member name"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("createdBy"),
                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("createdAt"),
                fieldWithPath("updatedBy").type(JsonFieldType.STRING).description("createdBy")
            )));
  }

  @Test
  void updatePost() throws Exception {
    //given
    MemberRequest memberRequest = new MemberRequest("김환", 25, "게임");
    Long savedMemberId = memberService.save(memberRequest);
    PostRequest postRequest = new PostRequest("제목", "내용", savedMemberId);
    Long savedPostId = postService.save(postRequest);

    PostRequest updatePostRequest = new PostRequest("수정된 제목", "수정된 내용", savedMemberId);

    //when & then
    mockMvc.perform(put("/post/" + savedPostId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(updatePostRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("updated title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("updated content"),
                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("member id who updated this")
            )));
  }

}