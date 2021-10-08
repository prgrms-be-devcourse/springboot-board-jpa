package com.prgrms.dlfdyd96.board.post.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.dto.UserDto;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
  private static final Long MIN_POST_ID = 0L;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void save_test() {
    // GIVEN
    PostDto postDto = PostDto.builder()
        .title("제목임다.")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
                    .name("일용")
                .age(26)
                .hobby("read book")
                .build()
        )
        .build();


    // WHEN
    Long postId = postService.save(postDto);

    // THEN
    assertThat(postId).isGreaterThanOrEqualTo(MIN_POST_ID);
  }

  @Test
  void saveCallTest() throws Exception {
    // GIVEN
    PostDto postDto = PostDto.builder()
        .title("제목임다.")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
                .name("일용")
                .age(26)
                .hobby("read book")
                .build()
        )
        .build();

    // WHEN // THEN
    ResultActions resultActions = mockMvc.perform(post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
        .andExpect(status().isOk())
        .andDo(print());
  }
}