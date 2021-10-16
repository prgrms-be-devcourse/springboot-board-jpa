package com.eden6187.jpaboard.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eden6187.jpaboard.common.ErrorCode;
import com.eden6187.jpaboard.controller.PostController.AddPostRequestDto;
import com.eden6187.jpaboard.exception.not_found.UserNotFoundException;
import com.eden6187.jpaboard.service.PostService;
import com.eden6187.jpaboard.test_data.PostMockData;
import com.eden6187.jpaboard.test_data.UserMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
    PostController.class
})
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ExtendWith(MockitoExtension.class)
@Slf4j
public class PostControllerTest {

  private static final AddPostRequestDto testAddPostDto = AddPostRequestDto.builder()
      .userId(UserMockData.TEST_ID)
      .title(PostMockData.TEST_TITLE)
      .content(PostMockData.TEST_CONTENT)
      .build();
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private PostService postService;

  @Test
  void addUserTest() throws Exception {

    when(postService.addPost(testAddPostDto)).thenReturn(PostMockData.TEST_ID);

    mockMvc.perform(post("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testAddPostDto))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-add",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("title"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("content")
                )
            )
        );
  }

  @Test
  void handleUserNotFoundException() throws Exception {
    when(postService.addPost(any())).thenThrow(new UserNotFoundException(ErrorCode.USER_NOT_FOUND));

    mockMvc.perform(post("/api/v1/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(testAddPostDto))
        )
        .andExpect(status().is4xxClientError())
        .andDo(print())
        .andDo(document("post-add/user-not-found",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING)
                        .description("serverDatetime"),
                    fieldWithPath("errorCode.statusCode").type(JsonFieldType.NUMBER)
                        .description("serverDatetime"),
                    fieldWithPath("errorCode.privateCode").type(JsonFieldType.STRING)
                        .description("privateCode"),
                    fieldWithPath("errorCode.message").type(JsonFieldType.STRING)
                        .description("message")
                )
            )
        );


  }

}
