package com.study.board.controller.post;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.study.board.controller.PostRestController;
import com.study.board.controller.support.RestDocsTestSupport;
import com.study.board.domain.post.domain.Post;
import com.study.board.domain.post.repository.PostRepository;
import com.study.board.domain.user.domain.User;
import com.study.board.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.study.board.fixture.Fixture.createUser;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PostRestControllerTest extends RestDocsTestSupport {

    @Autowired
    PostRestController controller;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    Post post1;
    Post post2;

    @BeforeEach
    void setUp() {
        User writer = createUser();
        userRepository.save(writer);

        post1 = postRepository.save(new Post("제목1", "내용1", writer));
        post2 = postRepository.save(new Post("제목2", "내용2", writer));
    }

    @Test
    void 게시글_페이징_조회() throws Exception {
        mockMvc.perform(get("/posts?page=0&size=20&sort=writtenDateTime,DESC")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpectAll(
                        jsonPath("$[0].postId").exists(),
                        jsonPath("$[0].title", "제목1").exists(),
                        jsonPath("$[0].content", "내용1").exists(),
                        jsonPath("$[0].writer", "득윤").exists(),
                        jsonPath("$[0].writtenDateTime").exists()
                ).andExpectAll(
                        jsonPath("$[1].postId").exists(),
                        jsonPath("$[1].title", "제목2").exists(),
                        jsonPath("$[1].content", "내용2").exists(),
                        jsonPath("$[1].writer", "득윤").exists(),
                        jsonPath("$[1].writtenDateTime").exists()
                ).andDo(
                        restDocs.document(
                                requestParameters(
                                        parameterWithName("page").description("조회할 페이지 번호 (0부터), default : 0"),
                                        parameterWithName("size").description("한 페이지 당 조회 게시글 수, default : 20"),
                                        parameterWithName("sort").description("정렬 기준 (fieldName),(ASC|DESC), default : writtenDateTime,DESC")
                                ),
                                responseFields(
                                        fieldWithPath("[].postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("[].writer").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("[].writtenDateTime").type(JsonFieldType.STRING).description("작성 일시")
                                )
                        )
                );
        ;
    }

    @Test
    void 게시글_단건_조회() throws Exception {
        Long targetPostId = post1.getId();

        mockMvc.perform(get("/posts/{postId}", targetPostId )
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.postId", targetPostId).exists(),
                        jsonPath("$.title", "제목1").exists(),
                        jsonPath("$.content", "내용1").exists(),
                        jsonPath("$.writer", "득윤").exists(),
                        jsonPath("$.writtenDateTime").exists()
                ).andDo(
                        restDocs.document(
                                pathParameters( // path 파라미터 정보 입력
                                        parameterWithName("postId").description("게시글 아이디")
                                ),
                                responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("writtenDateTime").type(JsonFieldType.STRING).description("작성 일시")
                                )
                        )
                );
    }

    @Test
    void 게시글_업로드() throws Exception {
        ObjectNode postRequest = objectMapper.createObjectNode()
                .put("title", "제목")
                .put("content", "내용");

        mockMvc.perform(post("/posts")
                        .header(AUTHORIZATION, "ndy")
                        .contentType(APPLICATION_JSON)
                        .content(createJson(postRequest)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.postId").exists(),
                        jsonPath("$.title", "제목").exists(),
                        jsonPath("$.content", "제목").exists(),
                        jsonPath("$.writer", "득윤").exists(),
                        jsonPath("$.writtenDateTime").exists()
                ).andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("인증 대체 - 로그인 아이디를 포함해주세요")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                                ), responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("writtenDateTime").type(JsonFieldType.STRING).description("작성 일시")
                                )
                        )
                );
    }

    @Test
    void 게시글_수정() throws Exception {
        Long postId = post1.getId();
        ObjectNode postRequest = objectMapper.createObjectNode()
                .put("title", "수정 제목")
                .put("content", "수정 내용");

        mockMvc.perform(put("/posts/" + postId)
                        .header(AUTHORIZATION, "ndy")
                        .contentType(APPLICATION_JSON)
                        .content(createJson(postRequest)))
                .andExpect(status().isOk())
                .andExpectAll(
                        jsonPath("$.postId").exists(),
                        jsonPath("$.title", "수정 제목").exists(),
                        jsonPath("$.content", "수정 제목").exists(),
                        jsonPath("$.writer", "득윤").exists(),
                        jsonPath("$.writtenDateTime").exists()
                ).andDo(
                        restDocs.document(
                                requestHeaders(
                                        headerWithName(AUTHORIZATION).description("인증 대체 - 로그인 아이디를 포함해주세요")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 수정 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 수정 내용")
                                ), responseFields(
                                        fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 수정 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 수정 내용"),
                                        fieldWithPath("writer").type(JsonFieldType.STRING).description("작성자 이름"),
                                        fieldWithPath("writtenDateTime").type(JsonFieldType.STRING).description("작성 일시")
                                )
                        )
                );
    }
}
