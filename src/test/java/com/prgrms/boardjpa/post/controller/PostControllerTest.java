package com.prgrms.boardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardjpa.domain.User;
import com.prgrms.boardjpa.post.dao.PostRepository;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.post.service.PostService;
import com.prgrms.boardjpa.user.dao.UserRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long postId;

    private User testUser;

    @BeforeEach
    void setUp() {
        User insertUser = new User("test user", 24, "soccer");

        testUser = userRepository.save(insertUser);

        PostReqDto postDto = PostReqDto.builder()
                .title("새 게시글")
                .content("새 내용")
                .userId(testUser.getId())
                .build();

        postId = postService.save(postDto).getId();

    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 다건 조회 api")
    void getAll() throws Exception {
        mockMvc.perform(get("/api/v1/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-multi-lookup",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("조회된 게시글 배열"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("페이징 정보"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("페이징 정렬 정보"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("페이징 정렬 정보"),

                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지 여부"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지인지 여부"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("페이지가 비었는지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("현재 페이지 내 게시글 개수"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("현재 페이지 index"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("페이지 내 게시글 최대 개수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 게시글 개수"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),

                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 기준 유무"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되어있는지 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되어있지 않은지 여부"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 기준 유무"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬이 되어있는지 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬이 되어있지 않은지 여부"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("현재 페이지 첫번째 게시글 index"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("현재 페이지 index"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 내 게시글 최대 개수"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 적용이 되어있는지 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 적용이 되어있지 않은지 여부"),

                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("data.content[].author").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.content[].createdAt").type(JsonFieldType.STRING).description("게시글 생성날짜"),
                                fieldWithPath("data.content[].author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 id"),
                                fieldWithPath("data.content[].author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )));
    }

    @Test
    @DisplayName("게시글 단건 조회 api")
    void getOne() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-single-lookup",
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("data.author").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성날짜"),

                                fieldWithPath("data.author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 id"),
                                fieldWithPath("data.author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }

    @Test
    @DisplayName("게시글 작성 api")
    void save() throws Exception {
        PostReqDto postDto = PostReqDto.builder()
                .title("새 게시글 작성 테스트")
                .content("새 내용 작성 테스트")
                .userId(testUser.getId())
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("게시글 본문")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("data.author").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성날짜"),

                                fieldWithPath("data.author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 id"),
                                fieldWithPath("data.author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));

    }

    @Test
    @DisplayName("게시글 작성 실패 api")
    void saveFail() throws Exception {
        PostReqDto postDto = PostReqDto.builder()
                .title("")
                .content("")
                .userId(testUser.getId())
                .build();

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 수정 api")
    void update() throws Exception {
        PostUpdateDto postDto = PostUpdateDto.builder()
                .title("수정된 제목")
                .content("수정된 본문")
                .build();

        mockMvc.perform(put("/api/v1/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정한 게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정한 게시글 본문")
                        ),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답시간"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("게시글 id"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("data.author").type(JsonFieldType.OBJECT).description("게시글 작성자 정보"),
                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("게시글 생성날짜"),

                                fieldWithPath("data.author.id").type(JsonFieldType.NUMBER).description("게시글 작성자 id"),
                                fieldWithPath("data.author.name").type(JsonFieldType.STRING).description("게시글 작성자 이름")
                        )
                ));
    }
}