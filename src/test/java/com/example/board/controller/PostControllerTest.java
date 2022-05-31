package com.example.board.controller;

import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.UserResponseDto;
import com.example.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.when;

@WebMvcTest
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    private Long postId = 1L;
    private Long userId = 2L;

    @Test
    @DisplayName("하나의 게시물을 조회한다")
    void getOneTest() throws Exception {
        PostResponseDto response = new PostResponseDto(
                postId,
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));
        when(postService.getOnePost(postId)).thenReturn(response);

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
    @DisplayName("게시물을 페이지 단위로 조회한다")
    void getPageTest() throws Exception {
        PageRequest page = PageRequest.of(0, 10);
        PostResponseDto response = new PostResponseDto(
                postId,
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));
        List<PostResponseDto> postList = new ArrayList<>();
        postList.add(response);
        Page<PostResponseDto> postResponseDtoPage = new PageImpl<>(postList);
        when(postService.getAllPostByPage(page)).thenReturn(postResponseDtoPage);

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
                                fieldWithPath("pageable").type(JsonFieldType.STRING).description("pageOption"),
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
    @DisplayName("게시물을 작성한다")
    void saveTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                "new-title",
                "new-content",
                new UserResponseDto(userId, "name", 25, "hobby"));

        PostResponseDto postResponseDto = new PostResponseDto(
                postId,
                "제목",
                "내용",
                new UserResponseDto(userId, "이름", 25, "취미"));
        when(postService.writePost(postRequestDto)).thenReturn(postResponseDto);

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
    @DisplayName("게시물을 업데이트 한다")
    void updateTest() throws Exception {
        PostRequestDto postRequestDto = new PostRequestDto(
                "new-title",
                "new-content",
                new UserResponseDto(userId, "이름", 25, "취미"));

        PostResponseDto postResponseDto = new PostResponseDto(
                postId,
                "new-title",
                "new-content",
                new UserResponseDto(userId, "이름", 25, "취미"));
        when(postService.updatePost(postId, postRequestDto)).thenReturn(postResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updatePost",
                        pathParameters(parameterWithName("id").description("postId")),
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
}