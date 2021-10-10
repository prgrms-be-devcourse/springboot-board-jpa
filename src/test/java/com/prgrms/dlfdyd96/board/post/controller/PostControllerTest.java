package com.prgrms.dlfdyd96.board.post.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.dlfdyd96.board.post.dto.PostDto;
import com.prgrms.dlfdyd96.board.post.dto.UserDto;
import com.prgrms.dlfdyd96.board.post.repository.PostRepository;
import com.prgrms.dlfdyd96.board.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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
  private PostRepository postRepository;

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

//  @AfterEach
//  void tearDown() {
//    postRepository.deleteAll();
//  }

  @Test
  void saveCallTest() throws Exception {
    // GIVEN
    PostDto postDto = PostDto.builder()
        .title("제목임다.")
        .content("내용1 \n 내용2")
        .userDto(
            UserDto.builder()
                .id(1L)
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
//        .andDo(document("post-save",
//            requestFields(
//                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
//                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
//                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
//                fieldWithPath("userDto.id").type(JsonFieldType.NULL).description("userDto.id"),
//                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
//                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
//                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
//            ),
//            responseFields(
//                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
//                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터"),
//                fieldWithPath("serverDatetime").type(JsonFieldType.NUMBER).description("응답시간")
//            )
//        ));
  }
}