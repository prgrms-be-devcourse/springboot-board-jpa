package kdt.cse0518.board.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.cse0518.board.post.converter.PostConverter;
import kdt.cse0518.board.post.dto.PostDto;
import kdt.cse0518.board.post.dto.RequestDto;
import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.post.factory.PostFactory;
import kdt.cse0518.board.post.service.PostService;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    public MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostService postService;
    @Autowired
    private PostConverter converter;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PostFactory postFactory;

    private User newUser1;
    private Post newPost1;

    @BeforeEach
    void setUp() {
        newUser1 = userFactory.createUser("사람1", 30, "취미1");
        newPost1 = postFactory.createPost("제목1", "내용1", newUser1);
    }

    @Test
    @DisplayName("GET /posts 동작 확인")
    void testGetPosts() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getAllPosts",
                        requestParameters(
                                parameterWithName("page").description("page index"),
                                parameterWithName("size").description("page size")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("data.content[]"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("data.content[].postId"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("data.content[].title"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("data.content[].content"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("data.content[].createdAt"),
                                fieldWithPath("data.content[].modifiedAt").type(JsonFieldType.STRING).description("data.content[].modifiedAt"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("data.pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("data.pageable.sort"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.empty"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("data.pageable.offset"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("data.pageable.pageNumber"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("data.pageable.pageSize"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("data.pageable.paged"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("data.pageable.unpaged"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("data.totalElements"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("data.totalPages"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("data.last"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("data.size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("data.number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("data.sort"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("data.sort.empty"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("data.sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("data.sort.unsorted"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("data.numberOfElements"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("data.first"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("data.empty"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )));
    }

    @Test
    @DisplayName("GET /posts/{postId} 동작 확인")
    void testGetPost() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", newPost1.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("getPost_by_postId",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("data.userId"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data.postId"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("data.postId"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("data.title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("data.content"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("data.createdAt"),
                                fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("data.modifiedAt"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )));
    }

    @Test
    @DisplayName("POST /posts 동작 확인")
    void testInsert() throws Exception {
        final Post newPost2 = postFactory.createPost("제목1", "내용1", newUser1);
        final PostDto dto = postService.findById(newPost2.getPostId());
        final RequestDto requestDto = converter.toRequestDto(dto);

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("insertPost",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )));
    }

    @Test
    @DisplayName("POST /posts/{postId} 동작 확인")
    void testUpdate() throws Exception {
        final Post newPost2 = postFactory.createPost("제목1", "내용1", newUser1);
        final PostDto dto = postService.findById(newPost2.getPostId());
        dto.update("바뀐 제목", "바뀐 내용");
        final RequestDto requestDto = converter.toRequestDto(dto);

        mockMvc.perform(put("/api/v1/posts/{postId}", newPost2.getPostId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("updatePost",
                        requestFields(
                                fieldWithPath("postId").type(JsonFieldType.NUMBER).description("postId"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                                fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("modifiedAt")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("statusCode"),
                                fieldWithPath("data").type(JsonFieldType.NUMBER).description("data"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("serverDatetime")
                        )));
    }
}