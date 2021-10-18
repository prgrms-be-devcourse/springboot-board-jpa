package com.kdt.devboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.devboard.post.Dto.PostRequest;
import com.kdt.devboard.post.repository.PostRepository;
import com.kdt.devboard.post.service.PostService;
import com.kdt.devboard.user.domain.User;
import com.kdt.devboard.user.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
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

import java.util.stream.IntStream;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
public class ControllerTest {

    private User user;
    private PostRequest postRequest;
    private Long userId;
    private Long postId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void saveTest() throws NotFoundException {
        user = User.builder()
                .name("김선호")
                .age(26)
                .hobby("아무것도 안하기")
                .build();

        User save = userRepository.save(user);
        userId = save.getId();

        postRequest = PostRequest.builder()
                .content("내용")
                .title("제목")
                .userId(userId)
                .build();

        postId = postService.save(postRequest);
    }

    @AfterEach
    void tearUp() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 단건 조회 요청 테스트")
    void getOne() throws Exception {
        mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        preprocessRequest(prettyPrint()),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createAt"),
                                fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 다건 조회 요청 테스트")
    void getAll() throws Exception {
        createPosts();
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정 요청 테스트")
    void updatePost() throws Exception {
        PostRequest updatedPost = PostRequest.builder()
                .postId(postId)
                .title("제에목")
                .content("내에에용")
                .userId(userId)
                .build();

        mockMvc.perform(put("/posts")
                        .content(objectMapper.writeValueAsString(updatedPost))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 식별자"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("createAt"),
                                fieldWithPath("data.updatedAt").type(JsonFieldType.STRING).description("updatedAt")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePost() throws Exception {
        mockMvc.perform(delete("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete",
                preprocessRequest(prettyPrint()),
                responseFields(
                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터")
                )
                ));
    }

    @Test
    @DisplayName("게시물을 생성할 수 있다.")
    void createPost() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest))
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        preprocessRequest(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("사용자 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터")
                        )
                ));

    }

    private void createPosts() {
        IntStream.range(1,10).mapToObj(i -> PostRequest.builder()
                .title("제목")
                .content("내용")
                .userId(userId)
                .build()).forEach(post-> {
            try {
                postService.save(post);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
    }

}
