package com.programmers.epicblues.jpa_board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.epicblues.jpa_board.dto.PostResponse;
import com.programmers.epicblues.jpa_board.repository.JpaUserRepository;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMapAdapter;
import testutil.EntityFixture;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

  final String GET_POSTS_URL = "/posts";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JpaUserRepository userRepository;

  @Autowired
  private ObjectMapper json;

  @ParameterizedTest
  @CsvSource({"1,3", "2,10", "3,20"})
  @DisplayName("요청한 Post 목록의 크기와 페이지를 정확히 반환해야 한다.")
  @Transactional
  void get_posts_with_page_index(String page, String size) throws Exception {

    // Given
    var savedPosts = EntityFixture.getPostList(100);
    var savedUser = EntityFixture.getUser();
    savedUser.addPosts(savedPosts);
    userRepository.save(savedUser);
    var params = new MultiValueMapAdapter<>(Map.of("page", List.of(page), "size", List.of(size)));

    // When
    ResultActions resultActions = mockMvc.perform(get(GET_POSTS_URL).params(params));

    // Then 요청한 페이지(from, volume)에 맞는 post를 반환해야 한다.
    int fromIndex = Integer.parseInt(page) * Integer.parseInt(size);
    int toIndex = fromIndex + Integer.parseInt(size);

    List<PostResponse> expectedResponse = PostResponse.from(savedPosts.subList(fromIndex, toIndex));
    resultActions.andExpectAll(
        status().isOk(),
        content().contentType(MediaType.APPLICATION_JSON),
        content().json(json.writeValueAsString(expectedResponse))
    );

  }

  @ParameterizedTest
  @CsvSource({",-4", "-1,-1", "-4,0"})
  @DisplayName("page나 size가 조건에 맞지 않으면 400 Bad Request 상태코드가 담기고, 어떤 필드가 잘못되었는지 알려주는 응답을 해야 한다.")
  void test_get_method_send_bad_request_when_request_payload_invalidate(String page, String size)
      throws Exception {

    // Given

    // When
    ResultActions resultActions = mockMvc.perform(
        get(GET_POSTS_URL).param("page", page).param("size", size)).andDo(print());

    // Then
    resultActions.andExpectAll(
        status().is(400),
        content().contentType(MediaType.APPLICATION_JSON),
        content().string(
            Matchers.allOf(
                Matchers.containsString("\"size\""),
                Matchers.containsString("\"page\""))
        )
    );

  }

}
