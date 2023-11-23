package org.programmers.dev.domain.post.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.programmers.dev.domain.post.application.PostService;
import org.programmers.dev.domain.post.controller.dto.CreatePostDto;
import org.programmers.dev.domain.post.controller.dto.PostResponse;
import org.programmers.dev.domain.post.controller.dto.UpdatePostDto;
import org.programmers.dev.domain.post.domain.entity.Post;
import org.programmers.dev.domain.user.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
@AutoConfigureRestDocs
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private PostResponse post;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        post = PostResponse.from(Post.builder()
            .id(1L)
            .title("title")
            .content("content")
            .createdAt(LocalDateTime.now())
            .user(User.builder()
                .id(1L)
                .name("name")
                .age(10)
                .hobby("hobby")
                .createdAt(LocalDateTime.now())
                .build())
            .build());
    }

    @Test
    void getAllPost() throws Exception {
        given(postService.getAll()).willReturn(List.of(post));

        mockMvc.perform(get("/posts"))
            .andExpect(status().isOk())
            .andDo(document("post-getAllPost",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                    fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("[].content").type(JsonFieldType.STRING).description("컨텐츠"),
                    fieldWithPath("[].user.id").type(JsonFieldType.NUMBER).description("유저 ID"),
                    fieldWithPath("[].user.name").type(JsonFieldType.STRING).description("유저 이름"),
                    fieldWithPath("[].user.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                    fieldWithPath("[].user.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                    fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                    fieldWithPath("[].createdBy").type(JsonFieldType.STRING).description("작성자")
                )));
    }


    @Test
    void getPostByIdTest() throws Exception {
        given(postService.findById(1L)).willReturn(post);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{id}", 1))
            .andExpect(status().isOk())
            .andDo(document("get-post-by-id",
                pathParameters(
                    parameterWithName("id").description("Post를 찾기 위해 사용되는 ID")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("포스트 ID"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("컨텐츠"),
                    fieldWithPath("user.id").type(JsonFieldType.NUMBER).description("유저 ID"),
                    fieldWithPath("user.name").type(JsonFieldType.STRING).description("유저 이름"),
                    fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                    fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("작성자")
                )));
    }

    @Test
    void createPostTest() throws Exception {
        CreatePostDto createPostDto = CreatePostDto.builder()
            .title("title")
            .content("content")
            .build();
        given(postService.create(createPostDto)).willReturn(1L);

        mockMvc.perform(
                post("/posts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createPostDto))
            )
            .andExpect(status().isCreated())
            .andDo(document("create-post",
                requestFields(
                    fieldWithPath("title").description("제목"),
                    fieldWithPath("content").description("내용")
                )
            ));
    }

    @Test
    void updatePostTest() throws Exception {
        UpdatePostDto updatePostDto = UpdatePostDto.builder()
            .title("title")
            .content("content")
            .build();
        given(postService.update(1L, updatePostDto)).willReturn(1L);

        mockMvc.perform(patch("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePostDto)))
            .andExpect(status().isOk())
            .andDo(document("update-post",
                pathParameters(
                    parameterWithName("id").description("Post를 업데이트 하기 위해 사용되는 ID ")
                ),
                requestFields(
                    fieldWithPath("title").description("업데이트 된 제목"),
                    fieldWithPath("content").description("업데이트 된 내용")
                )));
    }

}

