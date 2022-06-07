package com.devcourse.springjpaboard.post.controller;

import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_POST;
import static com.devcourse.springjpaboard.core.exception.ExceptionMessage.NOT_FOUND_USER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springjpaboard.application.post.controller.PostController;
import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.application.post.service.PostService;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.core.exception.GlobalExceptionHandler;
import com.devcourse.springjpaboard.core.exception.NotFoundException;
import com.devcourse.springjpaboard.post.controller.stub.PostStubs;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
class PostControllerTest {

  @InjectMocks
  private PostController postController;

  @Mock
  private PostService postService;

  private MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @BeforeEach
  public void init(RestDocumentationContextProvider restDocumentationContextProvider) {
    mockMvc = MockMvcBuilders.standaloneSetup(postController)
        .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
        .setControllerAdvice(new GlobalExceptionHandler())
        .apply(documentationConfiguration(restDocumentationContextProvider))
        .build();
  }

  @Test
  @DisplayName("게시글 작성 테스트")
  void writePostTest() throws Exception {
    // given
    CreatePostRequest request = PostStubs.createPostRequest();
    PostResponse response = PostStubs.createPostResponse();

    doReturn(response)
        .when(postService)
        .save(any(CreatePostRequest.class));

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    );
    // then
    resultActions.andExpect(status().isOk())
        .andDo(document("post-write",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("String"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("String"),
                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("Long")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));

  }

  @Test
  @DisplayName("존재하지 않는 유저로 글 작성을 시도할 경우 예외 발생")
  void unknownUserWriteTest() throws Exception {
    // given
    CreatePostRequest request = PostStubs.createPostRequest();
    doThrow(new NotFoundException(NOT_FOUND_USER))
        .when(postService)
        .save(any(CreatePostRequest.class));

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.post("/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    );

    // then
    MvcResult mvcResult = resultActions
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("data").exists())
        .andReturn();
  }

  @Test
  @DisplayName("게시글 업데이트 테스트")
  void updateTest() throws Exception {
    // given
    Long requestUser = 1L;
    UpdatePostRequest request = PostStubs.updatePostRequest();
    PostResponse response = PostStubs.updatePostResponse();

    doReturn(response)
        .when(postService)
        .update(any(Long.class), any(UpdatePostRequest.class));
    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.put("/posts/" + requestUser)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    );

    // then
    resultActions.andExpect(status().isOk())
        .andDo(document("post-update",
            requestFields(
                fieldWithPath("title").type(JsonFieldType.STRING).description("String"),
                fieldWithPath("content").type(JsonFieldType.STRING).description("String")
            ),
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("게시글 업데이트에 존재하지 않는 게시글 아이디가 전달되었을 경우 예외 발생")
  void unknownPostUpdateTest() throws Exception {
    // given
    Long requestPost = 2L;
    UpdatePostRequest request = PostStubs.updatePostRequest();

    doThrow(new NotFoundException(NOT_FOUND_POST))
        .when(postService)
        .update(any(Long.class), any(UpdatePostRequest.class));

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.put("/posts/" + requestPost)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request))
    );

    // then
    MvcResult mvcResult = resultActions
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("data").exists())
        .andReturn();
  }

  @Test
  @DisplayName("게시글 페이지 조회")
  void getAllPosts() throws Exception {
    // given
    Page<PostResponse> response = PostStubs.pagePostResponse();

    doReturn(response)
        .when(postService)
        .findAll(any(Pageable.class));
    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/posts")
            .param("page", "0")
            .param("size", "5")
    );
    // then
    resultActions.andDo(print())
        .andExpect(status().isOk())
        .andDo(document("post-find-page",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content[].content").type(JsonFieldType.STRING)
                    .description("본문"),
                fieldWithPath("data.pageable").type(JsonFieldType.STRING).description("pageable"),
                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER)
                    .description("전체 게시글 수"),
                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN)
                    .description("sort-empty"),
                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN)
                    .description("unsorted"),
                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sorted"),
                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER)
                    .description("numberOfElements"),
                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data-empty"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("게시글 번호로 게시글 조회")
  void getPostByIdTest() throws Exception {
    // given
    Long requestPost = 1L;
    PostResponse response = PostStubs.createPostResponse();

    doReturn(response)
        .when(postService)
        .findOne(any(Long.class));
    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/posts/" + requestPost)
    );

    // then
    resultActions.andExpect(status().isOk())
        .andDo(document("post-find-by-id",
            responseFields(
                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                fieldWithPath("data.content").type(JsonFieldType.STRING).description("본문"),
                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답시간")
            )
        ));
  }

  @Test
  @DisplayName("존재하지 않는 게시글을 조회할 경우 예외 발생")
  void unknownPostFindTest() throws Exception {
    // given
    Long requestPost = 2L;

    doThrow(new NotFoundException(NOT_FOUND_POST))
        .when(postService)
        .findOne(any(Long.class));

    // when
    ResultActions resultActions = mockMvc.perform(
        MockMvcRequestBuilders.get("/posts/" + requestPost)
    );

    // then
    MvcResult mvcResult = resultActions
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("data").exists())
        .andReturn();
  }
}