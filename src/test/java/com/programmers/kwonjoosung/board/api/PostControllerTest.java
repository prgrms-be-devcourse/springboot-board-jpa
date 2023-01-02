package com.programmers.kwonjoosung.board.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.kwonjoosung.board.model.dto.CreatePostRequest;
import com.programmers.kwonjoosung.board.model.dto.IdResponse;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import com.programmers.kwonjoosung.board.model.dto.UpdatePostRequest;
import com.programmers.kwonjoosung.board.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
class PostControllerTest {

    private final CreatePostRequest createPostRequest
            = new CreatePostRequest("제목입니다", "내용입니다");

    private final UpdatePostRequest updatePostRequest
            = new UpdatePostRequest("수정된 제목", "내용 수정 완료");

    private final PostInfo postInfo
            = new PostInfo("제목입니다", "내용입니다", "유저명", LocalDateTime.now());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    void getAllPostsTest() throws Exception {

        given(postService.findAllPost()).willReturn(List.of(postInfo, postInfo));

        mockMvc.perform(
                        get("/api/posts")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("post-getAll",
                        responseFields(
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].userName").type(JsonFieldType.STRING).description("유저명"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성일자")
                        ))
                );
    }

    @Test
    void getPostsToPageTest() throws Exception {

        Pageable pageRequest = PageRequest.of(0, 10, Sort.unsorted());
        Page<PostInfo> pageResponse = new PageImpl<>(List.of(postInfo, postInfo), pageRequest, 1);
        given(postService.findAllPost(any(Pageable.class)))
                .willReturn(pageResponse);

        mockMvc.perform(
                        get("/api/posts/pages")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("page", "0")
                                .param("size", "10")
                )
                .andExpect(status().isOk())
                .andDo(document("post-getPostsToPage",

                        responseFields(
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("content[].userName").type(JsonFieldType.STRING).description("유저명"),
                                fieldWithPath("content[].createdAt").type(JsonFieldType.STRING).description("생성일자"),
                                fieldWithPath("totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                                fieldWithPath("totalElements").type(JsonFieldType.NUMBER).description("게시글 전체 개수"),
                                fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("마지막 페이지인지 여부"),
                                fieldWithPath("size").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("number").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER).description("데이터 개수"),
                                fieldWithPath("first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지인지 여부"),
                                fieldWithPath("empty").type(JsonFieldType.BOOLEAN).description("페이지가 비어있는지 여부"),
                                fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 데이터 상태"),
                                fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부"),
                                fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("페이징 정렬 상태"),
                                fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("페이징 정렬 여부"),
                                fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("페이징 정렬 여부"),
                                fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER).description("게시글 오프셋"),
                                fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER).description("페이징 사이즈"),
                                fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 상태 여부"),
                                fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 상태 여부")
                        ))
                );
    }

    @Test
    void getPostByUserIdTest() throws Exception {

        given(postService.findPostByUserId(1L)).willReturn(List.of(postInfo));

        mockMvc.perform(
                        get("/api/posts/user/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("post-getByUserId",
                        pathParameters(
                                parameterWithName("id").description("유저 아이디")
                        ),
                        responseFields(
                                fieldWithPath("[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("[].userName").type(JsonFieldType.STRING).description("유저명"),
                                fieldWithPath("[].createdAt").type(JsonFieldType.STRING).description("생성일자")
                        ))
                );
    }

    @Test
    void getPostByPostIdTest() throws Exception {

        given(postService.findPostByPostId(1L)).willReturn(postInfo);

        mockMvc.perform(
                        get("/api/posts/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(document("post-getByPostId",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저명"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자")
                        ))
                );
    }

    @Test
    void createPostTest() throws Exception {

        given(postService.createPost(eq(1L), any(CreatePostRequest.class)))
                .willReturn(new IdResponse(1L));

        mockMvc.perform(
                        post("/api/posts/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createPostRequest))
                )
                .andExpect(status().isCreated())
                .andDo(document("post-create",
                        pathParameters(
                                parameterWithName("id").description("유저 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디")
                        ))
                );

    }

    @Test
    void updatePostTest() throws Exception {

        given(postService.updatePost(eq(1L), any(UpdatePostRequest.class)))
                .willReturn(postInfo);

        mockMvc.perform(
                        patch("/api/posts/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatePostRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("post-update",
                        pathParameters(
                                parameterWithName("id").description("게시글 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userName").type(JsonFieldType.STRING).description("유저명"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("생성일자")
                        ))
                );

    }
}