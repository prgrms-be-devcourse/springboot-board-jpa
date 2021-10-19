package com.toy.board.springbootboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toy.board.springbootboard.model.converter.PostConverter;
import com.toy.board.springbootboard.model.domain.Post;
import com.toy.board.springbootboard.model.domain.User;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.dto.UserDto;
import com.toy.board.springbootboard.model.repository.PostRepository;
import com.toy.board.springbootboard.model.repository.UserRepository;
import com.toy.board.springbootboard.model.service.PostService;
import javassist.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostControllerTest {
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

    @Autowired
    private PostConverter postConverter;

    private Long userId;

    @BeforeEach
    void setUp() throws NotFoundException {
        userId = userRepository.save(postConverter.convertUser(UserDto.builder()
                        .id(1L)
                        .name("minkyu")
                        .age(26)
                        .hobby("programming")
                        .build()))
                .getId();
    }

    @Test
    @Order(1)
    @DisplayName("Post 작성 요청 테스트")
    void savePostTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .userId(userId)
                .id(1L)
                .title("testTitle2")
                .content("testContent2")
                .build();

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }

    @Test
    @Order(2)
    @DisplayName("Posts 조회 요청 테스트")
    void getPostsTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .id(2L)
                .userId(1L)
                .title("testTitle2")
                .content("testContent2")
                .build();
        postService.save(postDto);

        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("postList",responseFields(
                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("status"),
                        fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("message"),
                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                        fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("content"),
                        fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                        fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("title"),
                        fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("content"),
                        fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("userId"),
                        fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                        fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                        fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                        fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                        fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                        fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                        fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                        fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                        fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                        fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                        fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                        fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                        fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                        fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                        fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                        fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                        fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                        fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                        fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                        fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty")
                )));
    }

    @Test
    @Order(3)
    @DisplayName("Id로 Post 조회 요청 테스트")
    void IdgetPostTest() throws Exception {
        mockMvc.perform(get("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("postById",
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("userId"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }

    @Test
    @Order(4)
    @DisplayName("post 수정 요청 테스트")
    void UpdatePostTest() throws Exception {
        PostDto UpdatePostDto = PostDto.builder()
                .userId(userId)
                .id(1L)
                .title("updateTitle2")
                .content("updateContent2")
                .build();
        mockMvc.perform(post("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(UpdatePostDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("userId")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("serverDateTime")
                        )));
    }
}