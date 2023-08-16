package com.jpaboard.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpaboard.post.application.PostService;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostCreate;
import com.jpaboard.post.ui.dto.PageParam;
import com.jpaboard.post.ui.PostUpdate;
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

import java.util.List;
import java.util.stream.IntStream;

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

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
    }

    private UserDto.Request getUserRequest(String name, int age, String hobby) {
        return new UserDto.Request(name, age, hobby);
    }

    private PostCreate.Request getPostCreateRequest(String title, String content, UserDto.Request response) {
        return new PostCreate.Request(title, content, response);
    }

    private List<PostCreate.Request> createPostRequestList(int number) {
        return IntStream.range(0, number)
                .boxed()
                .map((i) -> {
                    String name = "이름이름" + i;
                    int age = 11 * i;
                    String hobby = "사진찍기" + i;

                    String title = "제목" + i;
                    String content = "내용" + (111 * i);

                    UserDto.Request userRequest = getUserRequest(name, age, hobby);
                    return getPostCreateRequest(title, content, userRequest);
                }).toList();
    }

    @Test
    @DisplayName("모든 id를 조회하고 rest doc객체를 생성한다")
    void findPostAll_Test() throws Exception {
        // Given
        List<PostCreate.Request> postRequestList = createPostRequestList(3);

        for (PostCreate.Request postRequest : postRequestList) {
            postService.createPost(postRequest);
        }

        PageParam pageParam = new PageParam(0, 8);

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
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("게시글 작성자"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("내용"),

                                fieldWithPath("content[].user").type(JsonFieldType.OBJECT).description("작성자"),
                                fieldWithPath("content[].user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("content[].user.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("content[].user.hobby").type(JsonFieldType.STRING).description("취미"),

                                fieldWithPath("pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("pageable.sort").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),

                                fieldWithPath("pageable.offset").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.paged").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.STRING).description("Pageable 데이터").ignored(),

                                fieldWithPath("totalPages").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("totalElements").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("last").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("size").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("number").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),

                                fieldWithPath("sort").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("sort.empty").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("sort.sorted").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),

                                fieldWithPath("numberOfElements").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("first").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored(),
                                fieldWithPath("empty").type(JsonFieldType.OBJECT).description("Pageable 데이터").ignored()
                        )
                ));
    }


    @Test
    @DisplayName("id에 따라 post를 조회하고 rest doc객체를 생성한다")
    void findPostById_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostCreate.Request postRequest = getPostCreateRequest("제목22", "내용3124213", userRequest);

        PostCreate.Response post = postService.createPost(postRequest);

        // When, Then
        this.mockMvc.perform(get("/api/posts/{id}", post.id())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),

                                fieldWithPath("user.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("user.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("user.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    @DisplayName("post를 성공적으로 생성하고 rest doc객체를 생성한다")
    void createPost_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostCreate.Request postRequest = getPostCreateRequest("제목22", "내용3124213", userRequest);

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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"))
                ));
    }

    @Test
    @DisplayName("id로 찾은 post를 수정하고 rest doc객체를 생성한다")
    void updatePost_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("susuyeon", 24, "shopping");
        PostCreate.Request postRequest = getPostCreateRequest("제목22", "내용3124213", userRequest);

        PostCreate.Response post = postService.createPost(postRequest);

        PostUpdate.Request postUpdateRequest = new PostUpdate.Request("수정한 제목", "수정한 제목들");

        // When, Then
        this.mockMvc.perform(patch("/api/posts/{id}", post.id())
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
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        )
                ));
    }


    @Test
    @DisplayName("없는 id로 update를 시도하면 실패한다")
    void updatePost_Fail_Test() throws Exception {
        // Given
        UserDto.Request userRequest = getUserRequest("이름이름", 11, "사진찍기");
        PostCreate.Request postRequest = getPostCreateRequest("제목22", "내용3124213", userRequest);

        PostCreate.Response post = postService.createPost(postRequest);
        Long postId = post.id();

        PostUpdate.Request postUpdateRequest = new PostUpdate.Request("수정한 제목", "수정한 제목들");

        // When, Then
        this.mockMvc.perform(patch("/api/posts/{id}", postId + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}
