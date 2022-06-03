package com.programmers.springbootboardjpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboardjpa.dto.post.request.PostCreationRequest;
import com.programmers.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.programmers.springbootboardjpa.dto.user.request.UserCreationRequest;
import com.programmers.springbootboardjpa.repository.PostRepository;
import com.programmers.springbootboardjpa.repository.UserRepository;
import com.programmers.springbootboardjpa.service.PostService;
import com.programmers.springbootboardjpa.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void createPostTest() throws Exception {
        Long savedUserId = userService.saveUser(new UserCreationRequest("UserName", 33L, "UserHobby"));

        PostCreationRequest request = new PostCreationRequest(savedUserId, "PostTitle", "This is the content of the first post.");

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자(회원) id"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 게시글 데이터의 id"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 단건 조회 테스트")
    void getPostTest() throws Exception {
        Long savedUserId = userService.saveUser(new UserCreationRequest("UserName", 33L, "UserHobby"));
        Long savedPostId = postService.savePost(new PostCreationRequest(savedUserId, "PostTitle1", "PostContent1"));

        mockMvc.perform(get("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("게시글 작성자 id"),
                                fieldWithPath("data.cratedAt").type(JsonFieldType.STRING).description("게시글 작성된 날짜 및 시간"),
                                fieldWithPath("data.userResponse").type(JsonFieldType.OBJECT).description("회원 정보"),
                                fieldWithPath("data.userResponse.userId").type(JsonFieldType.NUMBER).description("회원 id"),
                                fieldWithPath("data.userResponse.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.userResponse.age").type(JsonFieldType.NUMBER).description("회원 나이"),
                                fieldWithPath("data.userResponse.hobby").type(JsonFieldType.STRING).description("회원 취미"),
                                fieldWithPath("data.userResponse.createdBy").type(JsonFieldType.STRING).description("회원 생성인"),
                                fieldWithPath("data.userResponse.createdAt").type(JsonFieldType.STRING).description("회원 생성 날짜")

                        )
                ));
    }

    @Test
    @DisplayName("게시물 다건 조회 테스트")
    void getAllPosts() throws Exception {
        Long savedUserId = userService.saveUser(new UserCreationRequest("UserName", 33L, "UserHobby"));
        for (int i = 1; i <= 12; i++) {
            postService.savePost(new PostCreationRequest(savedUserId, MessageFormat.format("PostTitle{0}", i), MessageFormat.format("PostContent{0}", i)));
        }

        mockMvc.perform(get("/api/v1/posts/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get-all",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("게시물 리스트"),
                                fieldWithPath("data.content[].postId").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("게시글 작성자 id"),
                                fieldWithPath("data.content[].cratedAt").type(JsonFieldType.STRING).description("게시글 작성된 날짜 및 시간"),
                                fieldWithPath("data.content[].userResponse").type(JsonFieldType.OBJECT).description("회원 정보"),
                                fieldWithPath("data.content[].userResponse.userId").type(JsonFieldType.NUMBER).description("회원 id"),
                                fieldWithPath("data.content[].userResponse.name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("data.content[].userResponse.age").type(JsonFieldType.NUMBER).description("회원 나이"),
                                fieldWithPath("data.content[].userResponse.hobby").type(JsonFieldType.STRING).description("회원 취미"),
                                fieldWithPath("data.content[].userResponse.createdBy").type(JsonFieldType.STRING).description("회원 생성인"),
                                fieldWithPath("data.content[].userResponse.createdAt").type(JsonFieldType.STRING).description("회원 생성 날짜"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이지 정보"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("페이지 정렬 정보"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("페이지 정렬 속성값의 존재 유무"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("페이지 정렬 상태(true이면 정렬)"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("페이지 정렬 상태(true이면 미정렬"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("해당 페이지의 첫 번째 게시글의 index(0부터 시작)"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("해당 페이지의 index(0부터 시작)"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("해당 페이지의 size"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("현재 Pageable의 pagination 정보 포함"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("현재 Pageable의 pagination 정보 미포함"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("현재 페이지가 마지막 페이지인지 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지의 수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 게시글의 개수"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("해당 페이지의 size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("해당 페이지의 index(0부터 시작)"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("페이지 정렬 속성값의 존재 유무"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("페이지 정렬 상태(true이면 정렬)"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("페이지 정렬 상태(true이면 미정렬"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("해당 페이지에 들어간 게시글의 개수"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("해당 페이지가 첫번째 페이지인지 여부"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("해당 페이지가 비었는지 여부")
                                )
                ));
    }

    @Test
    @DisplayName("게시물 수정 테스트")
    void updatePostTest() throws Exception {
        Long savedUserId = userService.saveUser(new UserCreationRequest("UserName", 33L, "UserHobby"));
        Long savedPostId = postService.savePost(new PostCreationRequest(savedUserId, "PostTitle1", "PostContent1"));

        PostUpdateRequest request = new PostUpdateRequest("PostTitle", "This is the content of the first post.");

        mockMvc.perform(patch("/api/v1/posts/{id}", savedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 게시글 내용")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("수정된 게시글 데이터의 id"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }

}