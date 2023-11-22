package kdt.jpa.board.post.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import kdt.jpa.board.common.support.ApiTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import kdt.jpa.board.post.domain.Post;
import kdt.jpa.board.post.repository.PostRepository;
import kdt.jpa.board.post.service.dto.request.CreatePostRequest;
import kdt.jpa.board.post.service.dto.request.EditPostRequest;
import kdt.jpa.board.user.domain.User;
import kdt.jpa.board.user.fixture.UserFixture;
import kdt.jpa.board.user.repository.UserRepository;

@DisplayName("[Post Api 테스트]")
class PostControllerTest extends ApiTestSupport {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("[게시물 등록 API 를 호출한다]")
    void savePost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        CreatePostRequest request = new CreatePostRequest("title", "content", user.getId());

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        List<Post> all = postRepository.findAll();
        assertThat(all).hasSize(1);

        perform.andExpect(status().isOk())
                .andDo(
                        document
                                .document(
                                        PayloadDocumentation.requestFields(
                                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                                PayloadDocumentation.fieldWithPath("userId").description("작성자 아이디")
                                        )
                                )
                );
    }

    @Test
    @DisplayName(("[단건 게시물 조회 API 를 호출한다]"))
    void getPost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post = new Post("title", "content", user);
        postRepository.save(post);

        //when
        ResultActions perform = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/api/posts/" + post.getId())
        );

        //then
        perform.andExpectAll(
                        status().isOk(),
                        jsonPath("$.title").value(post.getTitle()),
                        jsonPath("$.content").value(post.getContent()),
                        jsonPath("$.userName").value(user.getName()))
                .andDo(
                        document
                                .document(
                                        RequestDocumentation.pathParameters(
                                                RequestDocumentation.parameterWithName("id").description("검색할 게시물 아이디")
                                        ),
                                        PayloadDocumentation.responseFields(
                                                PayloadDocumentation.fieldWithPath("title").description("제목"),
                                                PayloadDocumentation.fieldWithPath("content").description("내용"),
                                                PayloadDocumentation.fieldWithPath("userName").description("작성자 이름")
                                        )
                                )
                );
    }

    @Test
    @DisplayName("[전체 게시물 페이징 조회 API 를 호출한다]")
    void getPosts() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post1 = new Post("title", "content", user);
        Post post2 = new Post("title", "content", user);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/posts")
        );

        //then
        perform.andExpect(status().isOk());
    }

    @Test
    @DisplayName("[게시물 수정 API 를 호출한다]")
    void updatePost() throws Exception {
        //given
        User user = userRepository.save(UserFixture.getUser());
        Post post = new Post("title", "content", user);
        postRepository.save(post);
        EditPostRequest request = new EditPostRequest(post.getId(), "title1", "content1");

        //when
        ResultActions perform = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        perform.andExpect(status().isOk());
    }
}
