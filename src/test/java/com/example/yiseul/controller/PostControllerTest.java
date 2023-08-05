package com.example.yiseul.controller;

import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostPageResponseDto;
import com.example.yiseul.dto.post.PostResponseDto;
import com.example.yiseul.dto.post.PostUpdateRequestDto;
import com.example.yiseul.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@Import(RestDocsAutoConfiguration.class)
@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    private PostResponseDto responseDto;

    @BeforeEach
    public void setUp(){
        responseDto = new PostResponseDto(1L,"jaws", "scary", "2023-07-19 17:00", "hihi");
    }

    @Test
    @DisplayName("게시글 생성에 성공한다.")
    void createPost() throws Exception {
        //given
        PostCreateRequestDto createRequestDto = new PostCreateRequestDto(1L, "jaws", "scary");

        given(postService.createPost(createRequestDto))
                .willReturn(responseDto);

        //when,then
        mvc.perform(post("/api/posts")
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(createRequestDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(responseDto)))

                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("memberId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시각"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자")
                        )
                ));
    }

    @Test
    @DisplayName("전체 게시글 페이징 조회에 성공한다.")
    void getAllPosts() throws Exception {
        // given
        List<PostResponseDto> postsList = Arrays.asList(
                responseDto,
                new PostResponseDto(2L,"title2", "content2","2023-07-30 18:00", "ja")
        );
        PostPageResponseDto responseDto = new PostPageResponseDto(postsList, 0, 2, 3, 4, true, false);

        Pageable pageable = PageRequest.of(0, 2);

        given(postService.getPosts(pageable))
                .willReturn(responseDto);

        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/posts")
                        .param("page", String.valueOf(pageable.getPageNumber()))
                        .param("size", String.valueOf(pageable.getPageSize())))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(responseDto)))

                .andDo(document("post-getPostsByPage",
                        responseFields(
                                fieldWithPath("postResponseDto.[].postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("postResponseDto.[].title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("postResponseDto.[].content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("postResponseDto.[].createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("postResponseDto.[].createdBy").type(JsonFieldType.STRING).description("createdBy"),

                                fieldWithPath("pageNumber").type(JsonFieldType.NUMBER).description("pageNumber"),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER).description("pageSize"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("isFirst").type(JsonFieldType.BOOLEAN).description("isFirst"),
                                fieldWithPath("isLast").type(JsonFieldType.BOOLEAN).description("isLast")
                )));
    }



    @Test
    @DisplayName("특정 게시글 조회에 성공한다.")
    void getPost() throws Exception {
        // given
        given(postService.getPost(anyLong()))
                .willReturn(responseDto);

        //when & then
        mvc.perform(get("/api/posts/{postId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().string(asJsonString(responseDto)))

                .andDo(document("post-getPost",
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성시각"),
                                fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 수정에 성공한다.")
    void updatePost() throws Exception {
        // given
        PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto("title1", "content1");

        doNothing().when(postService)
                .updatePost(anyLong(), any(PostUpdateRequestDto.class));

        // when & then
        mvc.perform(patch("/api/posts/{postId}", 1)
                        .contentType(APPLICATION_JSON)
                        .content(asJsonString(updateRequestDto)))
                        .andExpect(status().isOk())

                        .andDo(document("post-update",
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        )));

    }

    @Test
    @DisplayName("게시글 삭제에 성공한다.")
    void deletePost() throws Exception {
        //given
        doNothing().when(postService)
                .deletePost(anyLong());

        // when & then
        mvc.perform(delete("/api/posts/{postId}",1))
                .andExpect(status().isOk())

                .andDo(document("post-delete"));
    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        return mapper.writeValueAsString(obj);
    }
}