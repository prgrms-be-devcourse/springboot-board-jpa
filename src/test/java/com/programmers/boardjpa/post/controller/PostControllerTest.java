package com.programmers.boardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.boardjpa.post.dto.PostInsertRequestDto;
import com.programmers.boardjpa.post.dto.PostResponseDto;
import com.programmers.boardjpa.post.dto.PostUpdateRequestDto;
import com.programmers.boardjpa.post.service.PostService;
import com.programmers.boardjpa.user.entity.User;
import com.programmers.boardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

    @BeforeEach
    void setUp() {
        User user = new User("user", 10, "SLEEP");
        userRepository.save(user);
        
        userId = user.getId();

        PostInsertRequestDto postInsertRequestDto = new PostInsertRequestDto("제목", "내용", user.getId());
        PostResponseDto postResponseDto = postService.insertPost(postInsertRequestDto);

        postId = postResponseDto.postId();
    }

    @AfterEach
    void tearDown() {
        postService.deleteAllPosts();
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("PostService로부터 특정한 id의 Post를 가져와 보여줄 수 있다.")
    void testGetPostInService() throws Exception {
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
    @DisplayName("PostService로부터 모든 Post를 가져와 보여줄 수 있다.")
    void testGetPostsInService() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.data[0].postId").value(postId))
                .andExpect(jsonPath("$.data[0].title").value("제목"))
                .andExpect(jsonPath("$.data[0].content").value("내용"))
                .andExpect(jsonPath("$.data[0].userId").value(userId))
                .andDo(document("get-posts",
                        responseFields(
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("한 페이지 당 사이즈"),
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("페이지 데이터"),
                                fieldWithPath("data[].postId").type(JsonFieldType.NUMBER).description(postId),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("생성 일시"),
                                fieldWithPath("data[].updatedAt").type(JsonFieldType.STRING).description("수정 일시"),
                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description(userId)
                        ))
                );
    }

    @Test
    @DisplayName("PostService에 컨트롤러에서 받은 값들에 대한 Post를 생성하고 그 Post를 보여줄 수 있다.")
    void testInsertPostInService() throws Exception {
        // given
        PostInsertRequestDto postInsertRequestDto = new PostInsertRequestDto("제목2", "내용2", userId);

        // when - then
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postInsertRequestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.postId").value(postId + 1))
                .andExpect(jsonPath("$.title").value("제목2"))
                .andExpect(jsonPath("$.content").value("내용2"))
                .andExpect(jsonPath("$.userId").value(userId))
                .andDo(document("insert-post",
                        requestFields(
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
    @DisplayName("PostService에서 특정 Post를 수정하고 그 Post를 보여줄 수 있다.")
    void testUpdatePostInService() throws Exception {
        // given
        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("새로운 제목", "새로운 내용");


        // when - then
        mockMvc.perform(patch("/posts/{id}", postId)
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
