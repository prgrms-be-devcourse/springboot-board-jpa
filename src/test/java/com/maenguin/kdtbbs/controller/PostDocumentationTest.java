package com.maenguin.kdtbbs.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.beneathPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostAddResDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.dto.PostListDto;
import com.maenguin.kdtbbs.service.PostService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@WebMvcTest(PostRestController.class)
class PostDocumentationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private OptimisticLockTryer optimisticLockTryer;

    @Test
    void commonDocs() throws Exception {

        //given
        PostDto postDto = new PostDto(1L, "string", "string");
        given(postService.getPostById(any(Long.class)))
            .willReturn(postDto);
        //when
        ResultActions result = mockMvc.perform(
            get("/api/v1/posts/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andExpect(status().isOk())
            .andDo(document("common",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("success").type(JsonFieldType.BOOLEAN).description("요청 성공 여부"),
                    subsectionWithPath("data").type(JsonFieldType.OBJECT)
                        .description("요청 결과 데이터 (success = false면 null)"),
                    fieldWithPath("serverDateTime").type(JsonFieldType.STRING).description("응답 시간"),
                    fieldWithPath("error").type(JsonFieldType.OBJECT).description("에러 정보 (success = true면 null)")
                        .optional(),
                    fieldWithPath("error.errorCode").type(JsonFieldType.NUMBER).description("에러 코드"),
                    fieldWithPath("error.message").type(JsonFieldType.STRING).description("에러 메시지")
                )
            ));
    }

    @Test
    void searchAllPostsDocs() throws Exception {

        //given
        PostDto postDto = new PostDto(1L, "string", "string");
        Page<PostDto> page = new PageImpl<>(Collections.singletonList(postDto), PageRequest.of(0, 1), 0);
        PostListDto postListDto = new PostListDto(page);
        given(postService.getAllPosts(any(Pageable.class)))
            .willReturn(postListDto);
        //when
        ResultActions result = mockMvc.perform(
            get("/api/v1/posts")
                .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andExpect(status().isOk())
            .andDo(document("post-search",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("postList").type(JsonFieldType.ARRAY).description("게시글 목록"),
                    fieldWithPath("postList[].id").type(JsonFieldType.NUMBER).description("게시글 id"),
                    fieldWithPath("postList[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("postList[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                    fieldWithPath("pagination").type(JsonFieldType.OBJECT).description("pagination 정보"),
                    fieldWithPath("pagination.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수"),
                    fieldWithPath("pagination.totalElements").type(JsonFieldType.NUMBER).description("총 게시글 수"),
                    fieldWithPath("pagination.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지 번호"),
                    fieldWithPath("pagination.offset").type(JsonFieldType.NUMBER).description("페이징 offset"),
                    fieldWithPath("pagination.size").type(JsonFieldType.NUMBER).description("페이징 size")
                )
            ));
    }

    @Test
    void searchPostByIdDocs() throws Exception {

        //given
        PostDto postDto = new PostDto(1L, "string", "string");
        given(postService.getPostById(any(Long.class)))
            .willReturn(postDto);
        //when
        ResultActions result = mockMvc.perform(
            get("/api/v1/posts/{id}", postDto.getId())
                .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andExpect(status().isOk())
            .andDo(document("post-search-one",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("게시글 id")
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                )
            ));
    }

    @Test
    void registerPostDocs() throws Exception {

        //given
        PostAddDto postAddDto = new PostAddDto(1L, "string", "string");
        given(postService.savePost(any(PostAddDto.class)))
            .willReturn(new PostAddResDto(1L));
        //when
        ResultActions result = mockMvc.perform(
            post("/api/v1/posts")
                .content(objectMapper.writeValueAsString(postAddDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andExpect(status().isOk())
            .andDo(document("post-register",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 Id")
                )
            ));
    }

    @Test
    void editPostDocs() throws Exception {

        //given
        PostAddDto postAddDto = new PostAddDto(1L, "string", "string");
        given(postService.editPost(any(Long.class), any(PostAddDto.class)))
            .willReturn(new PostAddResDto(1L));
        //when
        ResultActions result = mockMvc.perform(
            post("/api/v1/posts/{id}", 1L)
                .content(objectMapper.writeValueAsString(postAddDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );
        //then
        result.andExpect(status().isOk())
            .andDo(document("post-edit",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(
                    parameterWithName("id").description("게시글 id")
                ),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("유저 id").optional(),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 내용")
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("게시글 Id")
                )
            ));
    }
}
