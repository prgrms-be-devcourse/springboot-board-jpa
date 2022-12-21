package com.example.board.domain.member.controller;

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
class MemberControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private MemberService memberService;

  @Autowired
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void newMember() throws Exception {
    //given
    MemberRequest memberRequest = new MemberRequest("김환", 25, "게임");

    //when & then
    mockMvc.perform(post("/member")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(memberRequest)))
        .andExpect(status().isCreated())
        .andDo(print())
        .andDo(document("member-save",
            requestFields(
                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
            )));
  }

  @Test
  void findMemberById() throws Exception {
    //given
    MemberRequest memberRequest = new MemberRequest("김환", 25, "게임");
    Long savedMemberId = memberService.save(memberRequest);
    postService.save(new PostRequest("으앙", "응아아아아앙", savedMemberId));

    //when & then
    mockMvc.perform(get("/member/" + savedMemberId))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("member-find-by-id",
            responseFields(
                fieldWithPath("id").type(JsonFieldType.NUMBER).description("member id"),
                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby"),
                fieldWithPath("posts[]").type(JsonFieldType.ARRAY).optional().description("list of posts"),
                fieldWithPath("posts[].id").type(JsonFieldType.NUMBER).description("post id"),
                fieldWithPath("posts[].title").type(JsonFieldType.STRING).description("title of post"),
                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("title of post"),
                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("title of post")
            )));
  }

}