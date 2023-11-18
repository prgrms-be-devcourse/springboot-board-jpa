package com.programmers.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.dto.PostControllerCreateRequestDto;
import com.programmers.springbootboardjpa.dto.PostControllerUpdateRequestDto;
import com.programmers.springbootboardjpa.entity.Post;
import com.programmers.springbootboardjpa.entity.User;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Transactional
@DisplayName("PostRestController Test")
class PostRestControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;
    private User user;

    @BeforeEach
    void init() {
        user = userRepository.save(User.builder()
                .name("username")
                .age(26)
                .hobby("testHobby")
                .build());
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void createPost() throws Exception {
        // Arrange
        PostControllerCreateRequestDto requestDto = new PostControllerCreateRequestDto("title", "content", user.getUserId());
        var post = post("/posts").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestDto));
        // Act & Assert
        mvc.perform(post)
                .andExpect(status().isOk())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("컨텐츠"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 ID")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("포스트 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("컨텐츠"),
                                fieldWithPath("data.user.userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("data.user.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.user.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.user.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부")
                        )));
    }

    @Test
    void updatePost() throws Exception {
        // Assert
        var entity = postRepository.save(Post.builder()
                .title("제목")
                .content("컨텐츠")
                .user(user)
                .build());
        PostControllerUpdateRequestDto requestDto = new PostControllerUpdateRequestDto("title", "content");
        var post = post("/posts/{id}", entity.getId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(requestDto));
        // Act & Assert
        mvc.perform(post)
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("컨텐츠")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부")
                        )));
    }

    @Test
    void getPostById() throws Exception {
        // Assert
        var entity = postRepository.save(Post.builder()
                .title("제목")
                .content("컨텐츠")
                .user(user)
                .build());
        var get = get("/posts/{id}", entity.getId());
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andDo(document("post-getById",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("포스트 ID"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("컨텐츠"),
                                fieldWithPath("data.user.userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("data.user.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.user.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.user.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부")
                        )));
    }

    @Test
    void getAllPost() throws Exception {
        // Assert
        postRepository.save(Post.builder()
                .title("제목")
                .content("컨텐츠")
                .user(user)
                .build());
        var get = get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(10));
        // Act & Assert
        mvc.perform(get)
                .andExpect(status().isOk())
                .andDo(document("post-getAllPost",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("포스트 ID"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("컨텐츠"),
                                fieldWithPath("data.content[].user.userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("data.content[].user.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.content[].user.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.content[].user.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("작성 시간"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("작성자"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("공백 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부(O)"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 여부 (X)"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이지 여부(O)"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이지 여부(X)"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 "),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 원소 개수"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("처음 여부"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("크기"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("번호"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 공백 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부(O)"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 여부(X)"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("원소 갯수"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("공백 여부"),
                                fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공 여부")
                        )));
    }
}