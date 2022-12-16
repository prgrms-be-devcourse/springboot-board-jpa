package com.assignment.board.controller;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.post.PostUpdateDto;
import com.assignment.board.entity.Hobby;
import com.assignment.board.entity.User;
import com.assignment.board.repository.PostRepository;
import com.assignment.board.repository.UserRepository;
import com.assignment.board.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    User user;
    User savedUser;
    PostRequestDto postRequestDto;
    PostResponseDto createdPostResponseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.SWIM);
        savedUser = userRepository.save(user);

        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("테스트");
        postRequestDto.setContent("테스트 입니다.");
        postRequestDto.setUserId(savedUser.getId());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @Test
    void createTest() throws Exception {
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Post TITLE"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Post CONTENT"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User ID")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));

    }

    @Test
    void getAllTest() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(2))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("데이터 배열"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.content[].userDto").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.content[].userDto.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.content[].userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.content[].userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.content[].userDto.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void getPostById() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        mockMvc.perform(get("/api/v1/posts/{postId}", createdPostResponseDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-by-id",
                        pathParameters(
                                parameterWithName("postId").description("게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

    @Test
    void updatePost() throws Exception {
        createdPostResponseDto = postService.createPost(postRequestDto);

        PostUpdateDto postUpdateDto = new PostUpdateDto();
        postUpdateDto.setTitle("수정 테스트");
        postUpdateDto.setContent("수정합니다");

        mockMvc.perform(patch("/api/v1/posts/{postId}", createdPostResponseDto.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NULL).description("NULL"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.userDto").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.userDto.id").type(JsonFieldType.NUMBER).description("유저 id"),
                                fieldWithPath("data.userDto.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("data.userDto.age").type(JsonFieldType.NUMBER).description("유저 나이"),
                                fieldWithPath("data.userDto.hobby").type(JsonFieldType.STRING).description("유저 취미"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("생성 날짜"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간")
                        )));
    }

}