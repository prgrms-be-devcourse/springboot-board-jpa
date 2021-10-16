package com.eden6187.jpaboard.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.eden6187.jpaboard.controller.PostController.UpdatePostRequestDto;
import com.eden6187.jpaboard.model.Post;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.repository.PostRepository;
import com.eden6187.jpaboard.repository.UserRepository;
import com.eden6187.jpaboard.test_data.PostMockData;
import com.eden6187.jpaboard.test_data.UserMockData;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostControllerIntegrationTest {

  private static Long userId;
  private static Long postId;
  @Autowired
  PostController postController;
  @Autowired
  UserRepository userRepository;
  @Autowired
  PostRepository postRepository;
  @Autowired
  MockMvc mockMvc;
  @Autowired
  ObjectMapper objectMapper;

  @BeforeEach
  void setUpData() {
    //given
    User user1 = User.builder()
        .name(UserMockData.TEST_NAME)
        .age(UserMockData.TEST_AGE)
        .hobby(UserMockData.TEST_HOBBY)
        .build();
    userId = userRepository.save(user1).getId();

    Post post = Post.builder()
        .content(PostMockData.TEST_CONTENT)
        .title(PostMockData.TEST_TITLE)
        .build();
    post.setUser(user1);
    postId = postRepository.save(post).getId();
  }

  @Test
  @Transactional
  void updatePost() throws Exception {
    //given
    String changedContent = "changed content";
    String changedTitle = "changed title";

    UpdatePostRequestDto updatePostRequestDto = UpdatePostRequestDto
        .builder()
        .title(changedTitle)
        .content(changedContent)
        .userId(userId)
        .build();

    //when
    mockMvc.perform(post("/api/v1/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(
                objectMapper.writeValueAsString(updatePostRequestDto)
            )
        )
        //then
        .andExpect(jsonPath("$.data.title").value(changedTitle))
        .andExpect(jsonPath("$.data.content").value(changedContent))
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-update",
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                ),
                responseFields(
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING)
                        .description("serverDatetime")
                )
            )
        );
  }

  @Test
  @Transactional
  void getSinglePost() throws Exception {
    //when
    mockMvc.perform(get("/api/v1/posts/{id}", postId))
        //then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.title").value(PostMockData.TEST_TITLE))
        .andExpect(jsonPath("$.data.content").value(PostMockData.TEST_CONTENT))
        .andExpect(jsonPath("$.data.userId").value(userId))
        .andExpect(jsonPath("$.data.userName").value(UserMockData.TEST_NAME))
        .andDo(print())
        .andDo(document("post-get-single",
                responseFields(
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING)
                        .description("serverDatetime"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                    fieldWithPath("data.userName").type(JsonFieldType.STRING).description("userName"),
                    fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("createdBy"),
                    fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createdAt")
                )
            )
        );
  }

}
