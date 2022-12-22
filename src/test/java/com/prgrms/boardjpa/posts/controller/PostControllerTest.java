package com.prgrms.boardjpa.posts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.posts.dto.PostDto;
import com.prgrms.boardjpa.posts.dto.PostRequest;
import com.prgrms.boardjpa.posts.service.PostService;
import com.prgrms.boardjpa.users.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  Long postId;

  @BeforeEach
  void createPostTest() {
    //given
    //insert into users(age, hobby, name) values(25, '', "gsw" ); test user data
    PostRequest postRequest = PostRequest.builder()
        .title("title")
        .content("content")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();

    //when
    PostDto post = postService.createPost(postRequest);
    postId = post.getPostId();

    //then
    assertThat(post.getTitle()).isEqualTo(postRequest.getTitle());
    assertThat(post.getContent()).isEqualTo(postRequest.getContent());
    assertThat(post.getUserDto()).usingRecursiveComparison().isEqualTo(postRequest.getUserDto());
  }

  @Test
  void getPostsCallTest() throws Exception {
    mockMvc.perform(get("/posts")
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("find-post-list",
//            responseFields(
              relaxedResponseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("content"),
                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("postId"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("data.content[].userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),

                /*
                전체 정보
                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("sort 정보"),
                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),

                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("offset"),
                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("paged"),
                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("unpaged"),

                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort 정보"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("unsorted"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                */

                fieldWithPath("responseMessage").type(JsonFieldType.STRING).description("응답 메세지")
            )
        ));
  }

  @Test
  void getPostCallTest() throws Exception {
    mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", postId)
        .contentType(MediaType.APPLICATION_JSON)
    )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("find-post",
            pathParameters(
                parameterWithName("postId").description("게시글 id")
            ),
            responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("data.userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                fieldWithPath("responseMessage").type(JsonFieldType.STRING).description("응답 메세지")
            )
        ));
  }

  @Test
  void createPostCallTest() throws Exception {
    //given
    //insert into users(age, hobby, name) values(25, '', "gsw" ); test user data
    PostRequest postRequest = PostRequest.builder()
        .title("title")
        .content("content")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();

    //when //then
    mockMvc.perform(post("/posts")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(postRequest))
    )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("create-post",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                ),
            responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("data.userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                fieldWithPath("responseMessage").type(JsonFieldType.STRING).description("응답 메세지")
            )
        ));
  }

  @Test
  void updatePostCallTest() throws Exception {
    //given
    //insert into users(age, hobby, name) values(25, '', "gsw" ); test user data
    String updateTitle = "updateTitle";
    PostRequest postRequest = PostRequest.builder()
        .title(updateTitle)
        .content("content")
        .userDto(
            UserDto.builder()
                .userId(1L)
                .name("gsw")
                .age(25)
                .hobby("")
                .build()
        )
        .build();

    //when //then
    mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postRequest))
        )
        .andExpect(status().isOk())
        .andDo(print())
        .andDo(document("update-post",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
            ),
            responseFields(
                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("postId"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("userDto"),
                fieldWithPath("data.userDto.userId").type(JsonFieldType.NUMBER).description("userDto.userId"),
                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby"),
                fieldWithPath("responseMessage").type(JsonFieldType.STRING).description("응답 메세지")
            )
        ));
  }
}