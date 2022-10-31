package com.programmers.epicblues.board.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.epicblues.board.dto.PostResponse;
import com.programmers.epicblues.board.entity.Post;
import com.programmers.epicblues.board.entity.User;
import com.programmers.epicblues.board.repository.JpaUserRepository;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMapAdapter;
import util.EntityFixture;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
class PostControllerTest {

  private static final ResponseFieldsSnippet POST_RESPONSE_FIELDS_SNIPPET =
      responseFields(
          fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
          fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
          fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 ID"),
          fieldWithPath("authorId").type(JsonFieldType.NUMBER).description("작성자 ID"),
          fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
          fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자")
      );
  final String BASE_URL = "/posts";
  @Autowired
  private MockMvc mockMvc;
  // TODO 3: PR 질문
  @Autowired
  private JpaUserRepository userRepository;
  @Autowired
  private ObjectMapper json;

  @ParameterizedTest
  @CsvSource({"1,3", "2,10", "3,20"})
  @DisplayName("요청한 Post 목록의 크기와 페이지를 정확히 반환해야 한다.")
  void get_posts_with_page_index(String page, String size) throws Exception {

    // Given
    var savedPosts = EntityFixture.getPostList(100);
    var savedUser = EntityFixture.getUser();
    savedUser.addPosts(savedPosts);
    userRepository.save(savedUser);
    var params = new MultiValueMapAdapter<>(Map.of("page", List.of(page), "size", List.of(size)));

    // When
    ResultActions resultActions = mockMvc.perform(get(BASE_URL).params(params));

    // Then 요청한 페이지(from, volume)에 맞는 post를 반환해야 한다.
    int fromIndex = Integer.parseInt(page) * Integer.parseInt(size);
    int toIndex = fromIndex + Integer.parseInt(size);

    List<PostResponse> expectedResponse = PostResponse.from(savedPosts.subList(fromIndex, toIndex));
    resultActions.andExpectAll(
        status().isOk(),
        content().contentType(MediaType.APPLICATION_JSON),
        content().json(json.writeValueAsString(expectedResponse))
    ).andDo(document("post-get-page",
        responseFields(
            fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("게시글 ID"),
            fieldWithPath("[].authorId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
            fieldWithPath("[].createdBy").type(JsonFieldType.STRING).description("작성자")
        )
    ));

  }

  @ParameterizedTest
  @CsvSource({",-4", "-1,-1", "-4,0"})
  @DisplayName("page나 size가 조건에 맞지 않으면 page가 0이고 size가 20인 기본 pageable 요청을 서비스에 위임해야 한다.")
  void test_get_method_send_bad_request_when_request_payload_invalidate(String page, String size) throws Exception {

    // given
    var savedPosts = EntityFixture.getPostList(100);
    var savedUser = EntityFixture.getUser();
    savedUser.addPosts(savedPosts);
    userRepository.saveAndFlush(savedUser);

    // When
    ResultActions resultActions = mockMvc.perform(
        get(BASE_URL).param("page", page).param("size", size)).andDo(print());

    List<PostResponse> expectedResponse = PostResponse.from(savedPosts.subList(0, 20));
    resultActions.andExpectAll(
        status().isOk(),
        content().contentType(MediaType.APPLICATION_JSON),
        content().json(json.writeValueAsString(expectedResponse))
    ).andDo(document("post-get-page",
        responseFields(
            fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
            fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("게시글 ID"),
            fieldWithPath("[].authorId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
            fieldWithPath("[].createdBy").type(JsonFieldType.STRING).description("작성자")
        )
    ));

  }

  @Test
  @DisplayName("등록된 postId를 통해 성공적으로 post를 가져올 수 있어야 한다.")
  void test_get_post_by_post_id() throws Exception {

    // Given
    User savedUser = EntityFixture.getUser();
    Post savedPost = EntityFixture.getFirstPost();
    savedUser.addPost(savedPost);
    savedUser = userRepository.save(savedUser);
    savedPost = savedUser.getPosts().get(0);

    // When
    ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/" + savedPost.getId()));

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isOk(),
        content().contentType(MediaType.APPLICATION_JSON),
        content().json(json.writeValueAsString(PostResponse.from(savedPost)))
    ).andDo(document("post-get-id", POST_RESPONSE_FIELDS_SNIPPET));
    ;
  }

  @Test
  @DisplayName("postId가 유효하지 않을 경우 404(Not Found)가 담긴 응답을 반환해야 한다.")
  void test_getById_responds_with_not_found_status_code_with_payload() throws Exception {

    // Given
    Map<String, String> expectedResponse = Map.of("message", "Invalid id");

    // When
    ResultActions resultActions = mockMvc.perform(get(BASE_URL + "/1"));

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isNotFound(),
        content().contentType(MediaType.APPLICATION_JSON),
        content().json(json.writeValueAsString(expectedResponse))
    ).andDo(document("post-get-id-failure",
        responseFields(
            fieldWithPath("message").type(JsonFieldType.STRING).description("설명")
        )
    ));
    ;

  }

  @Test
  @DisplayName("id와 입력값들을 받아서 post를 수정하고 수정한 결과물을 응답으로 받아야 한다.")
  void test_update_post() throws Exception {

    // Given
    User savedUser = EntityFixture.getUser();
    Post savedPost = EntityFixture.getFirstPost();
    savedUser.addPost(savedPost);
    savedUser = userRepository.save(savedUser);
    savedPost = savedUser.getPosts().get(0);

    var targetPostId = savedPost.getId();
    var updatedTitle = "updated!";
    var updatedContent = "updatedContent!";
    var requestPayload = json.writeValueAsString(
        Map.of("userId", savedUser.getId(), "postId", targetPostId, "title", updatedTitle, "content", updatedContent));

    // When
    ResultActions resultActions = mockMvc.perform(
        put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload)
    );

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isOk(),
        content().contentType(MediaType.APPLICATION_JSON),
        jsonPath("$.title").value(updatedTitle),
        jsonPath("$.content").value(updatedContent),
        jsonPath("$.authorId").value(savedUser.getId())
    ).andDo(document("post-update",
        requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("수정 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("수정 내용"),
            fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 ID"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID")
        ),
        POST_RESPONSE_FIELDS_SNIPPET));
    ;
  }

  @Test
  @DisplayName("update 요청 매개변수가 잘못됐을 경우, 400 Bad Request 예외와 안내 메시지를 던져야 한다.")
  void test_update_post_with_wrong_payload() throws Exception {

    // Given
    String wrongContent = "d";
    String wrongTitle = "t";
    String wrongPayload = json.writeValueAsString(
        Map.of("userId", 2, "postId", -3, "title", wrongTitle, "content", wrongContent));

    // When
    ResultActions resultActions = mockMvc.perform(
        put(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(wrongPayload));

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isBadRequest(),
        content().contentType(MediaType.APPLICATION_JSON),
        jsonPath("$.content").exists(),
        jsonPath("$.title").exists(),
        jsonPath("$.postId").exists()
    ).andDo(document("post-update-failure",
        requestFields(
            fieldWithPath("title").type(JsonFieldType.STRING).description("수정 제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("수정 내용"),
            fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 ID"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"))
        ,
        responseFields(
            fieldWithPath("content").type(JsonFieldType.STRING).description("잘못된 content 입력값 설명"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("잘못된 title 입력값 설명"),
            fieldWithPath("postId").type(JsonFieldType.STRING).description("잘못된 postId 입력값 설명")
        ))
    );

  }

  @Test
  @DisplayName("post 생성 요청에 대한 응답으로 완성된 post 정보들을 주어야 한다.")
  void create_post_success_case() throws Exception {

    // Given
    var persistedUser = EntityFixture.getUser();
    var savedUserId = userRepository.save(persistedUser).getId();
    var title = "newTitle";
    var content = "newContent";
    var requestPayload = json.writeValueAsString(
        Map.of("title", title, "content", content, "userId", savedUserId));

    // When
    ResultActions resultActions = mockMvc.perform(
        post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestPayload));

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isCreated(),
        header().exists(HttpHeaders.LOCATION),
        header().string(HttpHeaders.LOCATION, Matchers.containsString("/posts"))

    ).andDo(document("post-create",
        requestFields(
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
        )
    ));

  }

  @Test
  @DisplayName("post 생성 요청 매개변수가 잘못되었을 경우 400 상태 코드와 입력 오류 내용을 반환해야 한다.")
  void test_create_post_with_wrong_arguments() throws Exception {

    // Given
    Long wrongId = 0L;
    String wrongContent = "d";
    String wrongTitle = "t";
    String wrongPayload = json.writeValueAsString(
        Map.of("userId", wrongId, "title", wrongTitle, "content", wrongContent));

    // When
    ResultActions resultActions = mockMvc.perform(
        post(BASE_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(wrongPayload));

    // Then
    resultActions.andDo(print()).andExpectAll(
        status().isBadRequest(),
        content().contentType(MediaType.APPLICATION_JSON),
        jsonPath("$.content").exists(),
        jsonPath("$.title").exists(),
        jsonPath("$.userId").exists()
    ).andDo(document("post-create-failure",
        requestFields(
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 ID"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
            fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
        ),
        responseFields(
            fieldWithPath("content").type(JsonFieldType.STRING).description("잘못된 content 입력값 설명"),
            fieldWithPath("title").type(JsonFieldType.STRING).description("잘못된 title 입력값 설명"),
            fieldWithPath("userId").type(JsonFieldType.STRING).description("잘못된 userId 입력값 설명")
        )));
    ;

  }

}
