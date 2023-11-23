package com.devcourse.springbootboardjpahi.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.dto.UpdatePostRequest;
import com.devcourse.springbootboardjpahi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
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
        PostResponse postResponse = PostResponse.builder()
                .title(createPostRequest.title())
                .content(createPostRequest.content())
                .authorName(author.getName())
                .build();

        given(postService.create(createPostRequest))
                .willReturn(postResponse);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPostRequest)));

        // then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(postResponse.title())))
                .andExpect(jsonPath("$.content", is(postResponse.content())))
                .andExpect(jsonPath("$.authorName", is(postResponse.authorName())))
                .andDo(print());
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
                .andExpect(jsonPath("$.message", is("제목은 공백일 수 없습니다.")));
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
                .andExpect(jsonPath("$.message", is("내용이 존재하지 않습니다.")));
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
                .andExpect(jsonPath("$.message", is("유효하지 않은 유저 아이디입니다.")));
    }

    @DisplayName("[GET] 포스트를 상세 조회한다.")
    @Test
    void testFindById() throws Exception {
        // given
        long id = Math.abs(faker.number().randomDigitNotZero());
        String title = faker.book().title();
        String content = faker.shakespeare().kingRichardIIIQuote();
        String authorName = faker.name().firstName();
        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .authorName(authorName)
                .build();

        given(postService.findById(id))
                .willReturn(postDetailResponse);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/posts/" + id));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postDetailResponse.id()), Long.class))
                .andExpect(jsonPath("$.title", is(postDetailResponse.title())))
                .andExpect(jsonPath("$.content", is(postDetailResponse.content())))
                .andExpect(jsonPath("$.authorName", is(postDetailResponse.authorName())))
                .andDo(print());
    }

    @DisplayName("[PUT] 포스트 제목과 내용을 수정한다.")
    @Test
    void testUpdateById() throws Exception {
        // given
        UpdatePostRequest updatePostRequest = generateUpdateRequest();

        long id = Math.abs(faker.number().randomDigitNotZero());
        String authorName = faker.name().firstName();
        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(id)
                .title(updatePostRequest.title())
                .content(updatePostRequest.content())
                .authorName(authorName)
                .build();

        given(postService.updateById(id, updatePostRequest))
                .willReturn(postDetailResponse);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatePostRequest.title())))
                .andExpect(jsonPath("$.content", is(updatePostRequest.content())))
                .andDo(print());
    }

    @DisplayName("[PUT] 포스트 제목은 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void testUpdateBlankTitle(String title) throws Exception {
        // given
        long id = Math.abs(faker.number().randomDigitNotZero());
        String content = faker.shakespeare().hamletQuote();
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(title, content);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("제목은 공백일 수 없습니다.")));
    }

    @DisplayName("[PUT] 포스트 내용은 null일 수 없다.")
    @Test
    void testUpdateNullContent() throws Exception {
        // given
        long id = Math.abs(faker.number().randomDigitNotZero());
        String title = faker.book().title();
        UpdatePostRequest updatePostRequest = new UpdatePostRequest(title, null);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/posts/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("내용이 존재하지 않습니다.")));
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
        long id = Math.abs(faker.number().randomDigitNotZero());
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
}
