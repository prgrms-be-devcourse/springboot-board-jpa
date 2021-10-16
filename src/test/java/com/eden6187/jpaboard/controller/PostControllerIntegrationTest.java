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
  void getOne() throws Exception {
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

  @Test
  @Transactional
  void getAll() throws Exception {
    //when
    mockMvc.perform(get("/api/v1/posts", postId)
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
        )
        //then
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("post-get-all",
                responseFields(
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING)
                        .description("serverDatetime"),
                    fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                    fieldWithPath("data.content[].title").type(JsonFieldType.STRING)
                        .description("data.content[].title"),
                    fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                        .description("data.content[].content"),
                    fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER)
                        .description("data.content[].userId"),
                    fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING)
                        .description("data.content[].createdBy"),
                    fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING)
                        .description("data.content[].createdAt"),
                    fieldWithPath("data.content[].userName").type(JsonFieldType.STRING)
                        .description("data.content[].userName"),
                    fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("data.pageable.sort.sorted"),
                    fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("data.pageable.sort.empty"),
                    fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("data.pageable.sort.unsorted"),
                    fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("data.pageable.pageNumber"),
                    fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("data.pageable.pageSize"),
                    fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER)
                        .description("data.pageable.offset"),
                    fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("data.pageable.paged"),
                    fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("data.pageable.unpaged"),
                    fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                        .description("data.totalElements"),
                    fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER)
                        .description("data.totalPages"),
                    fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                    fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                        .description("data.numberOfElements"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                    fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                    fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("data.sort.empty"),
                    fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("data.sort.sorted"),
                    fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("data.sort.unsorted"),
                    fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                    fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty")
                )
            )
        );
  }

}
