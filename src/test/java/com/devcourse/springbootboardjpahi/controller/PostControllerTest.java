package com.devcourse.springbootboardjpahi.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devcourse.springbootboardjpahi.domain.User;
import com.devcourse.springbootboardjpahi.dto.CreatePostRequest;
import com.devcourse.springbootboardjpahi.dto.PostDetailResponse;
import com.devcourse.springbootboardjpahi.dto.PostResponse;
import com.devcourse.springbootboardjpahi.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        CreatePostRequest createPostRequest = generateRequest(author.getId());
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

    @DisplayName("[GET] 포스트를 상세 조회한다.")
    @Test
    void testFindById() throws Exception {
        // given
        long id = faker.number().randomDigitNotZero();
        String title = faker.book().title();
        String content = faker.shakespeare().kingRichardIIIQuote();
        String authorName = faker.name().firstName();
        PostDetailResponse postDetailResponse = PostDetailResponse.builder()
                .id(id)
                .title(title)
                .content(content)
                .authorName(authorName)
                .build();

        given(postService.findById(id)).willReturn(postDetailResponse);

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

    private CreatePostRequest generateRequest(Long userId) {
        String title = faker.book().title();
        String content = faker.shakespeare().hamletQuote();

        return new CreatePostRequest(title, content, userId);
    }

    private User generateAuthor() {
        String name = faker.name().firstName();
        int age = faker.number().randomDigitNotZero();
        String hobby = faker.esports().game();

        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }
}
