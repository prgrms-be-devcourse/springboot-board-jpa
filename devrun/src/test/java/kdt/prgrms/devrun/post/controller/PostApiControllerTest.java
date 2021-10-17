package kdt.prgrms.devrun.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.service.PostService;
import kdt.prgrms.devrun.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.IntStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("PostApiController 테스트")
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private final Long VALID_POST_ID = 1L;
    private final String BASE_URI = "/api/v1/posts";

    private User user;

    @BeforeAll
    void setUp() {

        user = User.builder()
            .loginId("kjt3520")
            .loginPw("1234")
            .age(27)
            .name("김지훈")
            .email("devrunner21@gmail.com")
            .build();

        userRepository.save(user);

        IntStream.range(0, 30).forEach(i -> postService.createPost(AddPostRequestDto.builder().title("제목 " + i).content("내용").createdBy(user.getLoginId()).build()));

    }

    @Test
    @DisplayName("게시글 페이징 목록 조회 테스트")
    void test_posts() throws Exception {

        mockMvc.perform(get(BASE_URI)
            .param("page", String.valueOf(0))
            .param("size", String.valueOf(10))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-list",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                    fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                    fieldWithPath("payload.totalCount").type(JsonFieldType.NUMBER).description("전체 데이터 수"),
                    fieldWithPath("payload.pageNo").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                    fieldWithPath("payload.pageSize").type(JsonFieldType.NUMBER).description("한 페이지 크기"),
                    fieldWithPath("payload.list").type(JsonFieldType.ARRAY).description("페이징 리스트"),
                    fieldWithPath("payload.list[].id").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("payload.list[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("payload.list[].createdBy").type(JsonFieldType.STRING).description("작성자 Id"),
                    fieldWithPath("payload.list[].createdAt").type(JsonFieldType.STRING).description("작성일자"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 데이터").optional(),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )));

    }

    @Test
    @DisplayName("게시글 단건(상세) 조회 테스트")
    void test_post() throws Exception {

        mockMvc.perform(get(BASE_URI + "/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-detail",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                    fieldWithPath("payload").type(JsonFieldType.OBJECT).description("응답 데이터").optional(),
                    fieldWithPath("payload.id").type(JsonFieldType.NUMBER).description("게시글 Id"),
                    fieldWithPath("payload.title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("payload.content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("payload.createdBy").type(JsonFieldType.STRING).description("작성자 Id"),
                    fieldWithPath("payload.createdAt").type(JsonFieldType.STRING).description("작성일자"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 데이터").optional(),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )));

    }

    @Test
    @DisplayName("게시글 생성 테스트")
    void test_create() throws Exception {

        final AddPostRequestDto postForm = AddPostRequestDto.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy(user.getLoginId())
            .build();

        mockMvc.perform(post(BASE_URI)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("post-save",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("생성할 게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("생성할 게시글 내용"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자 Id")
                ),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                    fieldWithPath("payload").type(JsonFieldType.NUMBER).description("생성된 게시글 Id"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 데이터").optional(),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )));

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void test_update() throws Exception {

        final EditPostRequestDto postForm = EditPostRequestDto.builder()
            .title("New Post Title")
            .content("New Post Content")
            .createdBy(user.getLoginId())
            .build();

        mockMvc.perform(patch(BASE_URI + "/{id}", VALID_POST_ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postForm)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-update",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("생성할 게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("생성할 게시글 내용"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("생성자 Id")
                ),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                    fieldWithPath("payload").type(JsonFieldType.NUMBER).description("생성된 게시글 Id"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 데이터").optional(),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )));
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void test_delete() throws Exception {

        final Long savedPostId = postService.createPost(AddPostRequestDto.builder().title("삭제할 게시글 제목").content("삭제할 게시글 내용").createdBy(user.getLoginId()).build());

        mockMvc.perform(delete(BASE_URI + "/{id}", savedPostId)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("post-delete",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("성공여부"),
                    fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("서버 응답 시간"),
                    fieldWithPath("payload").type(JsonFieldType.NULL).description("응답 데이터").optional(),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 데이터").optional(),
                    fieldWithPath("error.code").type(JsonFieldType.STRING).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )));

    }

}
