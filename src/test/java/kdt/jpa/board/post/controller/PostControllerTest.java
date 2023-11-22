package kdt.jpa.board.post.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import kdt.jpa.board.common.support.ApiTestSupport;
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
                        requestFields(
                            fieldWithPath("title").description("제목"),
                            fieldWithPath("content").description("내용"),
                            fieldWithPath("userId").description("작성자 아이디")
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
                .get("/api/posts/{id}", post.getId())
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
                        responseFields(
                            fieldWithPath("title").description("제목"),
                            fieldWithPath("content").description("내용"),
                            fieldWithPath("userName").description("작성자 이름")
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
            RestDocumentationRequestBuilders
                .get("/api/posts")
                .queryParam("page", String.valueOf(1))
                .queryParam("size", String.valueOf(5))
        );

        //then
        perform.andExpect(status().isOk())
            .andDo(
                document
                    .document(
                        RequestDocumentation.queryParameters(
                            RequestDocumentation.parameterWithName("page").description("요청 페이지"),
                            RequestDocumentation.parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                            fieldWithPath("responses.pageable.pageNumber").description("Page number"),
                            fieldWithPath("responses.pageable.pageSize").description("Page size"),
                            fieldWithPath("responses.pageable.sort.empty").description(
                                "Indicates if the sort is empty"),
                            fieldWithPath("responses.pageable.sort.unsorted").description(
                                "Indicates if the sort is unsorted"),
                            fieldWithPath("responses.pageable.sort.sorted").description(
                                "Indicates if the sort is sorted"),
                            fieldWithPath("responses.pageable.offset").description("Offset of the current page"),
                            fieldWithPath("responses.pageable.paged").description("Indicates if it's a paged result"),
                            fieldWithPath("responses.pageable.unpaged").description(
                                "Indicates if it's an unpaged result"),
                            fieldWithPath("responses.last").description("Indicates if this is the last page"),
                            fieldWithPath("responses.totalPages").description("Total number of pages"),
                            fieldWithPath("responses.totalElements").description("Total number of elements"),
                            fieldWithPath("responses.first").description("Indicates if this is the first page"),
                            fieldWithPath("responses.size").description("Page size"),
                            subsectionWithPath("responses.sort").ignored(),
                            fieldWithPath("responses.empty").description("Indicates if the response is empty"),
                            fieldWithPath("responses.number").description("Page number"),
                            fieldWithPath("responses.numberOfElements").description(
                                "Number of elements in the current page"),
                            fieldWithPath("responses.content[]").description("게시물")
                        )
                    )
            );
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
        perform.andExpect(status().isOk())
            .andDo(
                document
                    .document(
                        requestFields(
                            fieldWithPath("postId").description("게시물 id"),
                            fieldWithPath("title").description("제목"),
                            fieldWithPath("content").description("내용")
                        )
                    )
            );
    }
}
