package com.programmers.domain.post.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.domain.post.application.PostService;
import com.programmers.domain.post.ui.dto.PostCreateDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import com.programmers.domain.user.application.UserService;
import com.programmers.domain.user.ui.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @DisplayName("게시글을 생성할 수 있다.")
    @Test
    void createPost() throws Exception {
        //given
        Long userId = userService.createUser(new UserDto("유명한", 25, "농구"));
        PostCreateDto postCreateDto = new PostCreateDto("제목", "본문", userId);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postCreateDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("post-save",
                        PayloadDocumentation.requestFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("id").type(JsonFieldType.NUMBER).description("id")
                        )
                ))
                .andReturn();
    }

    @DisplayName("전체 게시글을 조회할 수 있다.")
    @Test
    void findPostList() throws Exception {
        //given
        Long userId = userService.createUser(new UserDto("유명한", 25, "농구"));

        PostCreateDto postCreateDto1 = new PostCreateDto("제목1", "본문1", userId);
        postService.createPost(postCreateDto1);

        PostCreateDto postCreateDto2 = new PostCreateDto("제목2", "본문2", userId);
        postService.createPost(postCreateDto2);

        PostCreateDto postCreateDto3 = new PostCreateDto("제목3", "본문3", userId);
        postService.createPost(postCreateDto3);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                        .param("page", "0")
                        .param("size", "20")
                        .param("sort", "id,desc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("posts-find",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("[].title").type(JsonFieldType.STRING).description("title"),
                                PayloadDocumentation.fieldWithPath("[].content").type(JsonFieldType.STRING).description("content"),
                                PayloadDocumentation.fieldWithPath("[].userId").type(JsonFieldType.NUMBER).description("userId"),
                                PayloadDocumentation.fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("postId")
                        )
                ))
                .andReturn();
    }

    @DisplayName("게시글을 조회할 수 있다.")
    @Test
    void findPost() throws Exception {
        //given
        Long userId = userService.createUser(new UserDto("유명한", 25, "농구"));
        PostCreateDto postCreateDto = new PostCreateDto("제목", "본문", userId);
        Long postId = postService.createPost(postCreateDto);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("post-find",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                PayloadDocumentation.fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId")
                        )
                ))
                .andReturn();
    }

    @DisplayName("게시글을 수정할 수 있다.")
    @Test
    void updatePost() throws Exception{
        //given
        Long userId = userService.createUser(new UserDto("유명한", 25, "농구"));
        PostCreateDto postCreateDto = new PostCreateDto("제목", "본문", userId);
        Long postId = postService.createPost(postCreateDto);

        PostUpdateDto postUpdateDto = new PostUpdateDto("제목수정", "본문수정");
        postService.updatePost(postUpdateDto, postId);

        //when, then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andDo(MockMvcRestDocumentation.document("post-update",
                        PayloadDocumentation.responseFields(
                                PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                PayloadDocumentation.fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId"),
                                PayloadDocumentation.fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId")
                        )
                ))
                .andReturn();
    }
}
