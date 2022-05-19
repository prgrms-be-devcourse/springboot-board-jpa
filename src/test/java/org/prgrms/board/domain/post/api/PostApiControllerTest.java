package org.prgrms.board.domain.post.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.board.domain.post.Service.PostService;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.post.request.PostUpdateRequest;
import org.prgrms.board.domain.post.response.PostSearchResponse;
import org.prgrms.board.domain.user.response.UserSearchResponse;
import org.prgrms.board.global.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    public void setUp() {
        session = new MockHttpSession();
        session.setAttribute("userId", 1L);
    }

    @AfterEach
    public void tearDown() {
        session.clearAttributes();
    }

    @Test
    void 게시글_다건조회_요청을_처리할수있다() throws Exception{
        //given
        UserSearchResponse userSearchResponse = UserSearchResponse.builder()
                .userId(1L)
                .firstName("우진")
                .lastName("박")
                .age(27)
                .email("dbslzld15@naver.com")
                .build();
        PostSearchResponse postSearchResponse1 = PostSearchResponse.builder()
                .postId(1L)
                .title("제목")
                .content("내용")
                .user(userSearchResponse)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        PostSearchResponse postSearchResponse2 = PostSearchResponse.builder()
                .postId(1L)
                .title("제목")
                .content("내용")
                .user(userSearchResponse)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        int pageSize = 10;
        int pageNumber = 0;
        Pageable pageable = Pageable.ofSize(pageSize);
        Page<PostSearchResponse> rawPages = new PageImpl<>(
                Arrays.asList(postSearchResponse1, postSearchResponse2), pageable, 2);
        PageDto<PostSearchResponse> postPages = PageDto.<PostSearchResponse>builder()
                .content(rawPages.getContent())
                .totalPages(rawPages.getTotalPages())
                .totalElements(rawPages.getTotalElements())
                .offset(rawPages.getPageable().getOffset())
                .pageSize(rawPages.getPageable().getPageSize())
                .pageNumber(rawPages.getPageable().getPageNumber())
                .build();
        when(postService.findAll(any())).thenReturn(postPages);
        //when
        //then
        mockMvc.perform(get("/api/v1/posts")
                        .session(session)
                        .param("page", String.valueOf(pageNumber))
                        .param("size", String.valueOf(pageSize)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getAll",
                        requestParameters(
                                parameterWithName("page").description("페이지 번호"),
                                parameterWithName("size").description("페이지 사이즈")
                        ),
                        responseFields(
                                fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                                        .description("조회된 게시글 목록"),
                                fieldWithPath("content[].postId").type(JsonFieldType.NUMBER)
                                        .description("게시글 아이디"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING)
                                        .description("게시글 내용"),
                                fieldWithPath("content[].user").type(JsonFieldType.OBJECT)
                                        .description("게시글 작성자"),
                                fieldWithPath("content[].user.userId").type(JsonFieldType.NUMBER)
                                        .description("작성자 아이디"),
                                fieldWithPath("content[].user.firstName").type(JsonFieldType.STRING)
                                        .description("작성자 이름"),
                                fieldWithPath("content[].user.lastName").type(JsonFieldType.STRING)
                                        .description("작성자 성"),
                                fieldWithPath("content[].user.age").type(JsonFieldType.NUMBER)
                                        .description("작성자 나이"),
                                fieldWithPath("content[].user.email").type(JsonFieldType.STRING)
                                        .description("작성자 이메일"),
                                fieldWithPath("content[].createdDate").type(JsonFieldType.STRING)
                                        .description("게시글 생성 일자"),
                                fieldWithPath("content[].modifiedDate").type(JsonFieldType.STRING)
                                        .description("게시글 수정 일자"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                                        .description("총 데이터 수"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                                        .description("총 페이지 수"),
                                fieldWithPath("offset").type(JsonFieldType.NUMBER)
                                        .description("offset"),
                                fieldWithPath("pageSize").type(JsonFieldType.NUMBER)
                                        .description("페이지 사이즈"),
                                fieldWithPath("pageNumber").type(JsonFieldType.NUMBER)
                                        .description("페이지 번호")
                        )
                ));
    }

    @Test
    void 게시글_단건조회_요청을_처리할수있다() throws Exception{
        //given
        long postId = 1L;
        UserSearchResponse userSearchResponse = UserSearchResponse.builder()
                .userId(1L)
                .firstName("우진")
                .lastName("박")
                .age(27)
                .email("dbslzld15@naver.com")
                .build();
        PostSearchResponse postSearchResponse = PostSearchResponse.builder()
                .postId(postId)
                .title("제목")
                .content("내용")
                .user(userSearchResponse)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        when(postService.findById(anyLong())).thenReturn(postSearchResponse);
        //when
        //then
        mockMvc.perform(get("/api/v1/posts/{postId}", postId)
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getOne",
                        responseFields(
                            fieldWithPath("postId").type(JsonFieldType.NUMBER)
                                    .description("게시글 아이디"),
                            fieldWithPath("title").type(JsonFieldType.STRING)
                                    .description("게시글 제목"),
                            fieldWithPath("content").type(JsonFieldType.STRING)
                                    .description("게시글 내용"),
                            fieldWithPath("user").type(JsonFieldType.OBJECT)
                                    .description("게시글 작성자"),
                            fieldWithPath("user.userId").type(JsonFieldType.NUMBER)
                                    .description("작성자 아이디"),
                            fieldWithPath("user.firstName").type(JsonFieldType.STRING)
                                    .description("작성자 이름"),
                            fieldWithPath("user.lastName").type(JsonFieldType.STRING)
                                    .description("작성자 성"),
                            fieldWithPath("user.age").type(JsonFieldType.NUMBER)
                                    .description("작성자 나이"),
                            fieldWithPath("user.email").type(JsonFieldType.STRING)
                                    .description("작성자 이메일"),
                            fieldWithPath("createdDate").type(JsonFieldType.STRING)
                                    .description("게시글 생성 일자"),
                            fieldWithPath("modifiedDate").type(JsonFieldType.STRING)
                                    .description("게시글 수정 일자")
                        )
                ));
    }

    @Test
    void 게시글_생성요청을_처리할수있다() throws Exception {
        //given
        PostCreateRequest createRequest = new PostCreateRequest("제목입니다.", "내용입니다.");
        //when
        //then
        mockMvc.perform(post("/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest))
                        .session(session))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("post-create",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("게시글 내용")
                        )
                ));
    }

    @Test
    void 게시글_수정요청을_처리할수있다() throws Exception{
        //given
        long postId = 1L;
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(
                "수정된 제목입니다.", "수정된 내용입니다.");
        when(postService.update(postId, postUpdateRequest)).thenReturn(postId);
        //when
        //then
        mockMvc.perform(post("/api/v1/posts/{postId}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdateRequest))
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING)
                                        .description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING)
                                        .description("게시글 내용")
                        )
                ));
    }

    @Test
    void 게시글_삭제요청을_처리할수있다() throws Exception{
        //given
        long postId = 1L;
        //when
        //then
        mockMvc.perform(delete("/api/v1/posts/{postId}", postId)
                        .session(session))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-delete")
                );
    }
}