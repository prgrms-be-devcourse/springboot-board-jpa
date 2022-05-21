package com.sdardew.board.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sdardew.board.domain.post.CreatePostDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @DisplayName("전체 Post 조회")
  void testGetAll() throws Exception {
    mockMvc.perform(get("/posts").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andDo(document("find-all"));
  }

  @Test
  void testGetPost() throws Exception {
    CreatePostDto postDto = new CreatePostDto("title", "content", 1L);
    mockMvc.perform(post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postDto)))
      .andExpect(status().isOk())
      .andDo(document("find-by-id"));
  }
}