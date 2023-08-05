package me.kimihiqq.springbootboardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import me.kimihiqq.springbootboardjpa.post.domain.Post;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostCreateRequest;
import me.kimihiqq.springbootboardjpa.post.dto.request.PostUpdateRequest;
import me.kimihiqq.springbootboardjpa.post.repository.PostRepository;
import me.kimihiqq.springbootboardjpa.user.domain.User;
import me.kimihiqq.springbootboardjpa.user.dto.UserCreateRequest;
import me.kimihiqq.springbootboardjpa.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;

    @BeforeEach
    void setUp() {
        User temp = new UserCreateRequest("you", 40, "play").toEntity();
        user = userRepository.save(temp);
        System.out.println("User ID: " + user.getId()); // 이 부분이 추가된 로그 코드입니다.

    }

    @Test
    @DisplayName("Get all posts")
    public void getPostsTest() throws Exception {

        for (int i = 0; i < 5; i++) {
            Post post = Post.builder()
                    .title("Test title " + i)
                    .content("Test content " + i)
                    .user(user)
                    .build();
            postRepository.save(post);
        }

        mockMvc.perform(get("/api/v1/posts"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-posts",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("content[].id").type(JsonFieldType.NUMBER).description("Post Id"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("Post Title"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("Post Content"),
                                fieldWithPath("content[].userId").type(JsonFieldType.NUMBER).description("Post User Id"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("Post Created At"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("Sort Empty"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("Sorted"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("Unsorted"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("Offset"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("Page Number"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("Page Size"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("Paged"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("Unpaged"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("Last"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("Total Pages"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("Total Elements"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("Size"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("Number"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("Sort Empty"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("Sorted"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("Unsorted"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("Number of Elements"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("First"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("Empty")
                        )
                ));
    }

    @Test
    @DisplayName("Get single post")
    public void getPostTest() throws Exception {
        // Create a post for test
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .user(user)
                .build();
        postRepository.save(post);

        mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-post",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post ID")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Post Id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Post Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Post Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User Id"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Post createdAt")
                        )
                ));
    }

    @Test
    @DisplayName("Create new post")
    public void createPostTest() throws Exception {
        System.out.println("User ID: " + user.getId());

        PostCreateRequest requestDto = new PostCreateRequest(" Test title", "Test content", user.getId());
        System.out.println("User ID: " + requestDto);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Post Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Post Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User Id")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Post Id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Post Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Post Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User Id"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Post createdAt")
                        )
                ));
    }

    @Test
    @DisplayName("Update a post")
    public void updatePostTest() throws Exception {
        // Create a post for test
        Post post = Post.builder()
                .title("Test title")
                .content("Test content")
                .user(user)
                .build();
        postRepository.save(post);

        PostUpdateRequest requestDto = new PostUpdateRequest("Updated title", "Updated content");

        mockMvc.perform(put("/api/v1/posts/{id}", post.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("Post ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("New Post Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("New Post Content")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("Post Id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("Post Title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("Post Content"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("User Id"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Post createdAt")
                        )
                ));
    }
}
