package com.devcourse.springbootboardjpahi.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@AutoConfigureRestDocs
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
        long id = Math.abs(faker.number().randomDigitNotZero());
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

        // docs
        actions.andDo(print())
                .andDo(document("post-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("authorName").type(JsonFieldType.STRING).description("Author Name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Creation Datetime")
                        )
                ));
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(postService.findById(id))
                .willReturn(postDetailResponse);

        // when
        MockHttpServletRequestBuilder docsGetRequest = RestDocumentationRequestBuilders.get("/api/v1/posts/{id}", id);
        ResultActions actions = mockMvc.perform(docsGetRequest);

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(postDetailResponse.id()), Long.class))
                .andExpect(jsonPath("$.title", is(postDetailResponse.title())))
                .andExpect(jsonPath("$.content", is(postDetailResponse.content())))
                .andExpect(jsonPath("$.authorName", is(postDetailResponse.authorName())));

        // docs
        actions.andDo(print())
                .andDo(document("post-find-single",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("authorName").type(JsonFieldType.STRING).description("Author Name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Creation Datetime"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING)
                                        .description("Last Update Datetime")
                        )
                ));
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(postService.updateById(id, updatePostRequest))
                .willReturn(postDetailResponse);

        // when
        MockHttpServletRequestBuilder docsPutRequest = RestDocumentationRequestBuilders.put("/api/v1/posts/{id}", id);
        ResultActions actions = mockMvc.perform(docsPutRequest.contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostRequest)));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updatePostRequest.title())))
                .andExpect(jsonPath("$.content", is(updatePostRequest.content())));

        // docs
        actions.andDo(print())
                .andDo(document("post-update",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("authorName").type(JsonFieldType.STRING).description("Author Name"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Creation Datetime"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING)
                                        .description("Last Update Datetime")
                        )
                ));
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

        // docs
        actions.andDo(print())
                .andDo(document("post-page-no-content"));
    }

    @DisplayName("[GET] 포스트를 페이징 조회한다.")
    @Test
    void testFind() throws Exception {
        // given
        long totalCount = 35;
        int defaultPageSize = 10;
        int totalPages = (int) Math.ceil((double) totalCount / defaultPageSize);
        long contentSize = totalCount % defaultPageSize;
        List<PostResponse> postResponses = generatePostResponses(contentSize);
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

        // docs
        actions.andDo(print())
                .andDo(document("post-page",
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("Page"),
                                parameterWithName("size").description("Contents per Page")
                        ),
                        responseFields(
                                fieldWithPath("isEmpty").type(JsonFieldType.BOOLEAN)
                                        .description("True if no post is found"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                                        .description("Total number of pages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                                        .description("Total number of all posts"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("Post Id"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("Title"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("Content"),
                                fieldWithPath("content[].authorName").type(JsonFieldType.STRING)
                                        .description("Author Name"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                                        .description("Creation Datetime")
                        )
                ));
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

    private PostResponse generatePostResponse() {
        long id = Math.abs(faker.number().randomDigitNotZero());
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

    private List<PostResponse> generatePostResponses(long count) {
        List<PostResponse> postResponses = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            postResponses.add(generatePostResponse());
        }

        return postResponses;
    }
}
