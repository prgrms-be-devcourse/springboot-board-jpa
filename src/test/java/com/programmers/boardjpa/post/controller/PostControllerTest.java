package com.programmers.boardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.dto.PostUpdateRequestDto;
import com.programmers.boardjpa.post.service.PostService;
import com.programmers.boardjpa.user.entity.User;
import com.programmers.boardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    
    private Long userId;
    private Long postId;

    @BeforeAll
    void setUp() {
        User user = new User("user", 10, "SLEEP");
        userRepository.save(user);
        
        userId = user.getId();

        PostInsertRequestDto postInsertRequestDto = new PostInsertRequestDto(1L, "제목", "내용", user.getId());
        PostResponseDto postResponseDto = postService.insertPost(postInsertRequestDto);

        postId = postResponseDto.postId();
    }


    @Test
    void getPostInService() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.title").value("제목"))
                .andExpect(jsonPath("$.content").value("내용"))
                .andExpect(jsonPath("$.userId").value(userId))
                .andDo(document("get-post",
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description(postId),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일시"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 일시"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description(userId)
                        ))
                );
    }

    @Test
    void getPostsInService() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].postId").value(postId))
                .andExpect(jsonPath("$.[0].title").value("제목"))
                .andExpect(jsonPath("$.[0].content").value("내용"))
                .andExpect(jsonPath("$.[0].userId").value(userId))
                .andDo(document("get-posts",
                        responseFields(
                                fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description(postId),
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성 일시"),
                                fieldWithPath("[].updatedAt").type(JsonFieldType.STRING).description("수정 일시"),
                                fieldWithPath("[].userId").type(JsonFieldType.NUMBER).description(userId)
                        ))
                );
    }

    @Test
    void insertPostInService() throws Exception {
        // given
        PostInsertRequestDto postInsertRequestDto = new PostInsertRequestDto(2L, "제목2", "내용2", userId);

        // when - then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInsertRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.postId").value(2L))
                .andExpect(jsonPath("$.title").value("제목2"))
                .andExpect(jsonPath("$.content").value("내용2"))
                .andExpect(jsonPath("$.userId").value(userId))
                .andDo(document("insert-post",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description(2L),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목2"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용2"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description(userId)
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description(2L),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목2"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용2"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일시"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 일시"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description(userId)
                        ))
                );
    }

    @Test
    void updatePostInService() throws Exception {
        // given
        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("새로운 제목", "새로운 내용");


        // when - then
        mockMvc.perform(patch("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.postId").value(postId))
                .andExpect(jsonPath("$.title").value("새로운 제목"))
                .andExpect(jsonPath("$.content").value("새로운 내용"))
                .andExpect(jsonPath("$.userId").value(userId))
                .andDo(document("update-post",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description(postId),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("새로운 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("새로운 내용")
                        ),
                        responseFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description(postId),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("새로운 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("새로운 내용"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성 일시"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("수정 일시"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description(userId)
                        ))
                );
    }
}
