package com.devcourse.springbootboardjpahi.controller;

import static com.devcourse.springbootboardjpahi.message.PostExceptionMessage.BLANK_TITLE;
import static com.devcourse.springbootboardjpahi.message.PostExceptionMessage.INVALID_USER_ID;
import static com.devcourse.springbootboardjpahi.message.PostExceptionMessage.NO_SUCH_POST;
import static com.devcourse.springbootboardjpahi.message.PostExceptionMessage.NO_SUCH_USER;
import static com.devcourse.springbootboardjpahi.message.PostExceptionMessage.NULL_CONTENT;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PageResponse;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.dto.UpdatePostRequest;
import com.devcourse.springbootboardjpahi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(PostController.class)
class PostControllerTest {

    static final Faker faker = new Faker();

    @MockBean
    PostService postService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("[POST] 포스트를 추가한다.")
    @Test
    void testCreate() throws Exception {
        // given
        User author = generateAuthor();
        CreatePostRequest createPostRequest = generateCreateRequest(author.getId());
        long id = generateId();
        PostResponse postResponse = PostResponse.builder()
                .id(id)
                .title(createPostRequest.title())
                .content(createPostRequest.content())
                .authorName(author.getName())
                .createdAt(LocalDateTime.now())
                .build();

        given(postService.create(createPostRequest))
                .willReturn(postResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(createPostRequest.title())))
                .andExpect(jsonPath("$.content", is(createPostRequest.content())))
                .andExpect(jsonPath("$.authorName", is(author.getName())));
    }

    @DisplayName("[POST] 존재하지 않는 유저의 포스트를 생성할 수 없다.")
    @Test
    void testCreateNoSuchUser() throws Exception {
        // given
        long id = generateId();
        CreatePostRequest createPostRequest = generateCreateRequest(id);

        given(postService.create(createPostRequest))
                .willThrow(new NoSuchElementException(NO_SUCH_USER));

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(NO_SUCH_USER)));
    }

    @DisplayName("[POST] 포스트 제목은 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void testCreateBlankTitle(String title) throws Exception {
        // given
        User author = generateAuthor();
        String content = faker.esports().player();
        CreatePostRequest createPostRequest = new CreatePostRequest(title, content, author.getId());

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(BLANK_TITLE)));
    }

    @DisplayName("[POST] 포스트 내용은 null일 수 없다.")
    @Test
    void testCreateNullContent() throws Exception {
        // given
        User author = generateAuthor();
        String title = faker.esports().player();
        CreatePostRequest createPostRequest = new CreatePostRequest(title, null, author.getId());

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(NULL_CONTENT)));
    }

    @DisplayName("[POST] 포스트 작성자 id는 음수일 수 없다.")
    @Test
    void testCreateNegativeId() throws Exception {
        // given
        long id = faker.number().numberBetween(-1000, -1);
        CreatePostRequest createPostRequest = generateCreateRequest(id);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(INVALID_USER_ID)));
    }

    @DisplayName("[GET] 포스트를 상세 조회한다.")
    @Test
    void testFindById() throws Exception {
        // given
        long id = generateId();
        String title = faker.book().title();
        String content = faker.shakespeare().kingRichardIIIQuote();
        String authorName = faker.name().firstName();
        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .authorName(authorName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(postService.findById(id))
                .willReturn(postDetailResponse);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/posts/{id}", id));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postDetailResponse.id()), Long.class))
                .andExpect(jsonPath("$.title", is(postDetailResponse.title())))
                .andExpect(jsonPath("$.content", is(postDetailResponse.content())))
                .andExpect(jsonPath("$.authorName", is(postDetailResponse.authorName())));
    }

    @DisplayName("[GET] 존재하지 않는 포스트를 조회할 수 없다.")
    @Test
    void testFindByIdNoSuchPost() throws Exception {
        // given
        long id = generateId();

        given(postService.findById(id))
                .willThrow(new NoSuchElementException(NO_SUCH_POST));

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/posts/{id}", id));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(NO_SUCH_POST)));
    }

    @DisplayName("[PUT] 포스트 제목과 내용을 수정한다.")
    @Test
    void testUpdateById() throws Exception {
        // given
        UpdatePostRequest updatePostRequest = generateUpdateRequest();

        long id = generateId();
        String authorName = faker.name().firstName();
        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(id)
                .title(updatePostRequest.title())
                .content(updatePostRequest.content())
                .authorName(authorName)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(postService.updateById(id, updatePostRequest))
                .willReturn(postDetailResponse);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatePostRequest.title())))
                .andExpect(jsonPath("$.content", is(updatePostRequest.content())));
    }

    @DisplayName("[PUT] 존재하지 않는 포스트를 수정할 수 없다.")
    @Test
    void testUpdateByIdNoSuchPost() throws Exception {
        // given
        long id = generateId();
        UpdatePostRequest updatePostRequest = generateUpdateRequest();

        given(postService.updateById(id, updatePostRequest))
                .willThrow(new NoSuchElementException(NO_SUCH_POST));

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(NO_SUCH_POST)));
    }

    @DisplayName("[PUT] 포스트 제목은 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void testUpdateBlankTitle(String title) throws Exception {
        // given
        long id = generateId();
        String content = faker.shakespeare().hamletQuote();
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(title, content);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(BLANK_TITLE)));
    }

    @DisplayName("[PUT] 포스트 내용은 null일 수 없다.")
    @Test
    void testUpdateNullContent() throws Exception {
        // given
        long id = generateId();
        String title = faker.book().title();
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(title, null);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is(NULL_CONTENT)));
    }

    @DisplayName("[GET] 포스트가 없을 때 204 상태 코드를 반환한다.")
    @Test
    void testFindNoContent() throws Exception {
        // given
        PageResponse<PostResponse> page = PageResponse.<PostResponse>builder()
                .isEmpty(true)
                .totalPages(1)
                .totalElements(0L)
                .content(Collections.emptyList())
                .build();

        given(postService.getPage(any()))
                .willReturn(page);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/posts"));

        // then
        actions.andExpect(status().isNoContent());
    }

    @DisplayName("[GET] 포스트를 페이징 조회한다.")
    @Test
    void testFind() throws Exception {
        // given
        long totalCount = 35;
        int defaultPageSize = 10;
        int totalPages = (int) Math.ceil((double) totalCount / defaultPageSize);
        long contentSize = totalCount % defaultPageSize;
        List<PostResponse> postResponses = generatePostResponsesOrderByAsc(contentSize);
        PageResponse<PostResponse> page = PageResponse.<PostResponse>builder()
                .isEmpty(false)
                .totalPages(totalPages)
                .totalElements(totalCount)
                .content(postResponses)
                .build();

        given(postService.getPage(any()))
                .willReturn(page);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/posts")
                .param("page", "3")
                .param("size", "10"));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.isEmpty", is(false)))
                .andExpect(jsonPath("$.totalPages", is(totalPages)))
                .andExpect(jsonPath("$.totalElements", is(totalCount), Long.class))
                .andExpect(jsonPath("$.content", hasSize((int) contentSize)));
    }

    private CreatePostRequest generateCreateRequest(Long userId) {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new CreatePostRequest(title, content, userId);
    }

    private UpdatePostRequest generateUpdateRequest() {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new UpdatePostRequest(title, content);
    }

    private User generateAuthor() {
        long id = generateId();
        String name = faker.name().firstName();
        int age = faker.number().numberBetween(0, 120);
        String hobby = faker.esports().game();

        return User.builder()
                .id(id)
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    private PostResponse generatePostResponse() {
        long id = generateId();
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();
        User author = generateAuthor();

        return PostResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .authorName(author.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }

    private List<PostResponse> generatePostResponsesOrderByAsc(long count) {
        List<PostResponse> postResponses = new ArrayList<>();

        for (long id = 1; id <= count; id++) {
            String title = faker.book().title();
            String content = faker.shakespeare().hamletQuote();
            User author = generateAuthor();

            PostResponse postResponse = PostResponse.builder()
                    .id(id)
                    .title(title)
                    .content(content)
                    .authorName(author.getName())
                    .createdAt(LocalDateTime.now())
                    .build();

            postResponses.add(postResponse);
        }

        return postResponses;
    }

    private long generateId() {
        return Math.abs(faker.number().randomDigitNotZero());
    }
}
