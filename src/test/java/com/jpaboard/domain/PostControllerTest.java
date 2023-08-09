package com.jpaboard.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.dto.PostDto;
import com.jpaboard.user.ui.dto.UserDto;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private JpaPostRepository postRepository;

    private List<PostDto.Request> postRequestList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        postRequestList.clear();
    }

    private UserDto.Request getUserRequest(String name, int age, String hobby) {
        return UserDto.Request.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

    private PostDto.Request getPostRequest(String title, String content, UserDto.Request response) {
        return PostDto.Request.builder()
                .title(title)
                .content(content)
                .user(response)
                .build();
    }

    @Test
    @DisplayName("모든 id를 조회하고 rest doc객체를 생성한다")
    void findPostAll_Test() throws Exception {
        // Given
        for (int i = 1; i <= 3; i++) {
            String name = "이름이름" + i;
            int age = 11 * i;
            String hobby = "사진찍기" + i;

            String title = "제목" + i;
            String content = "내용" + (111 * i);

            UserDto.Request userRequest = getUserRequest(name, age, hobby);
            PostDto.Request postRequest = getPostRequest(title, content, userRequest);

            postRequestList.add(postRequest);
        }

        for (PostDto.Request postRequest : postRequestList) {
            postService.createPost(postRequest);
        }

        Map<String, Integer> pageParam = new HashMap<>();
        pageParam.put("page", 0);
        pageParam.put("size", 8);

        // When, Them
        this.mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pageParam)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        requestFields(
                                fieldWithPath("page").type(JsonFieldType.NUMBER).description("페이지 쪽수"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 글 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),

                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("제목"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),

                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),

                                fieldWithPath("data.content[].user").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("data.content[].user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.content[].user.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.content[].user.hobby").type(JsonFieldType.STRING).description("취미"),

                                fieldWithPath("data.pageable.sort").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),

                                fieldWithPath("data.pageable.offset").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),

                                fieldWithPath("data.totalPages").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.totalElements").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.last").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.size").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.number").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),

                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),

                                fieldWithPath("data.numberOfElements").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.first").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("data.empty").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored()
                        )
                ));
    }

    @Test
    @DisplayName("id에 따라 post를 조회하고 rest doc객체를 생성한다")
    void findPostById_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostDto.Request postRequest = getPostRequest("제목22", "내용3124213", userRequest);

        Long postId = postService.createPost(postRequest);

        // When, Then
        this.mockMvc.perform(get("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),

                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글내용"),

                                fieldWithPath("data.user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.user.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.user.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    @DisplayName("post를 성공적으로 생성하고 rest doc객체를 생성한다")
    void createPost_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostDto.Request postRequest = getPostRequest("제목22", "내용3124213", userRequest);

        postService.createPost(postRequest);

        // When, Then
        this.mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("user").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("id로 찾은 post를 수정하고 rest doc객체를 생성한다")
    void updatePost_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("susuyeon", 24, "shopping");
        PostDto.Request postRequest = getPostRequest("제목22", "내용3124213", userRequest);

        Long postId = postService.createPost(postRequest);

        PostDto.PostUpdateRequest postUpdateRequest = PostDto.PostUpdateRequest.builder()
                .title("수정한 제목")
                .content("수정한 내용들")
                .build();

        // When, Then
        this.mockMvc.perform(patch("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("데이터")
                        )
                ));
    }


    @Test
    @DisplayName("없는 id로 update를 시도하면 실패한다")
    void updatePost_Fail_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostDto.Request postRequest = getPostRequest("제목22", "내용3124213", userRequest);

        Long postId = postService.createPost(postRequest);

        PostDto.PostUpdateRequest postUpdateRequest = PostDto.PostUpdateRequest.builder()
                .title("수정한 제목")
                .content("수정한 내용들")
                .build();

        // When, Then
        this.mockMvc.perform(patch("/api/posts/{id}", postId + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
