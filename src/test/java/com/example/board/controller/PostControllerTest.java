package com.example.board.controller;

import com.example.board.domain.User;
import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.repository.PostRepository;
import com.example.board.repository.UserRepository;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
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

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    private Long postId;
    private Long userId;

    @BeforeEach
    void setup() {
        // given
        User user = userRepository.save(new User("name", 25, "hobby"));
        userId = user.getId();

        PostRequestDto postRequestDto = new PostRequestDto(
                "title",
                "content",
                new UserResponseDto(userId, "name", 25, "hobby"));

        // when
        postId = postService.writePost(postRequestDto).id();

        // then
        assertThat(postId).isGreaterThan(0);
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
    }

    @Test
    void getOneTest() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getOnePost",
                        pathParameters(parameterWithName("id").description("postId")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("author"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("authorId"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("authorName"),
                                fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("authorAge"),
                                fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("authorHobby")
                        )
                ));
    }

    @Test
    void getPageTest() throws Exception {
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getAllPostByPage",
                        requestParameters(
                                parameterWithName("page").description("pageNumber"),
                                parameterWithName("size").description("sizeOfPage")
                        ),
                        responseFields(
                                fieldWithPath("content[]").type(JsonFieldType.ARRAY).description("content"),
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("postTitle"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("postContent"),
                                fieldWithPath("content[].author").type(JsonFieldType.OBJECT).description("postAuthor"),
                                fieldWithPath("content[].author.id").type(JsonFieldType.NUMBER).description("postAuthorId"),
                                fieldWithPath("content[].author.name").type(JsonFieldType.STRING).description("postAuthorName"),
                                fieldWithPath("content[].author.age").type(JsonFieldType.NUMBER).description("postAuthorAge"),
                                fieldWithPath("content[].author.hobby").type(JsonFieldType.STRING).description("postAuthorHobby"),
                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("pageOption"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT).description("pageOption.sortOption"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageOption.sortOption.empty"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageOption.sortOption.sorted"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageOption.sortOption.unsorted"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("pageOption.offset"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("pageOption.pageSize"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageOption.pageNumber"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("pageOption.paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageOption.unpaged"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("isLastPage"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sortOption"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("sortOption.empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("sortOption.sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("sortOption.unsorted"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("isFirst"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("isEmpty")
                        )
                ));
    }

    @Test
    void saveTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                "new-title",
                "new-content",
                new UserResponseDto(userId, "name", 25, "hobby"));

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("writePost",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("author"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("authorId"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("authorName"),
                                fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("authorAge"),
                                fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("authorHobby")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("author"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("authorId"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("authorName"),
                                fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("authorAge"),
                                fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("authorHobby")
                        )
                ));
    }

    @Test
    void updateTest() throws Exception {
        PostResponseDto postResponseDto = postService.getOnePost(postId);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postResponseDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updatePost",
                        pathParameters(parameterWithName("id").description("postId")),
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("author"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("authorId"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("authorName"),
                                fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("authorAge"),
                                fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("authorHobby")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("author").type(JsonFieldType.OBJECT).description("author"),
                                fieldWithPath("author.id").type(JsonFieldType.NUMBER).description("authorId"),
                                fieldWithPath("author.name").type(JsonFieldType.STRING).description("authorName"),
                                fieldWithPath("author.age").type(JsonFieldType.NUMBER).description("authorAge"),
                                fieldWithPath("author.hobby").type(JsonFieldType.STRING).description("authorHobby")
                        )
                ));
    }
}