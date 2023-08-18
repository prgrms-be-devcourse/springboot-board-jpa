package com.programmers.jpa_board.post.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpa_board.global.exception.NotFoundException;
import com.programmers.jpa_board.post.application.PostService;
import com.programmers.jpa_board.post.domain.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static com.programmers.jpa_board.global.exception.ExceptionMessage.NOT_FOUND_POST;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class PostRestDocsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    void 저장_성공() throws Exception {
        //given
        PostDto.CreatePostRequest request = new PostDto.CreatePostRequest("제목-범철", "내용이야", 1L);
        PostDto.CommonResponse response = new PostDto.CommonResponse(1L, "제목", "내용", 1L, "신범철", LocalDateTime.now());

        given(postService.save(request))
                .willReturn(response);

        //when & then
        this.mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userId").type(JsonFieldType.NUMBER).description("작성자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")

                        )
                ));
    }

    @Test
    void 단건_조회_성공() throws Exception {
        //given
        Long postId = 3L;
        PostDto.CommonResponse response = new PostDto.CommonResponse(postId, "제목", "내용", 1L, "신범철", LocalDateTime.now());

        given(postService.getOne(postId))
                .willReturn(response);

        //when & then
        this.mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")
                        )
                ));
    }

    @Test
    void 게시물_단건_조회_실패() throws Exception {
        //given
        Long postId = 1L;

        given(postService.getOne(postId))
                .willThrow(new NotFoundException(NOT_FOUND_POST.getMessage()));

        //when & then
        mockMvc.perform(get("/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("post-get-fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("데이터")
                                )
                ));
    }

    @Test
    void 페이징_조회_성공() throws Exception {
        //given
        PostDto.CommonResponse response1 = new PostDto.CommonResponse(1L, "제목1", "내용1", 1L, "신범철", LocalDateTime.now());
        PostDto.CommonResponse response2 = new PostDto.CommonResponse(2L, "제목2", "내용2", 1L, "신범철", LocalDateTime.now());

        PageRequest pageable = PageRequest.of(0, 10);
        PageImpl<PostDto.CommonResponse> responses = new PageImpl<>(List.of(response1, response2), pageable, 2);

        given(postService.getPage(any(Pageable.class)))
                .willReturn(responses);

        //when & then
        this.mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-getPages",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                subsectionWithPath("data").description("데이터"),
                                fieldWithPath("data.content").type(JsonFieldType.ARRAY).description("배열"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("글 아아디"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.content[].userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.content[].createAt").type(JsonFieldType.STRING).description("생성 시간"),
                                subsectionWithPath("data.pageable").description("페이징 정보"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 여부"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 크기"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 가능 여부"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징되지 않은 여부"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 요소 수"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("데이터 크기"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("페이지 번호"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬된 여부"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬되지 않은 여부"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("첫 페이지 여부"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("요소 수"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("빈 값 여부")
                        )
                ));
    }

    @Test
    void 수정_성공() throws Exception {
        //given
        Long postId = 1L;
        PostDto.UpdatePostRequest updatePostRequest = new PostDto.UpdatePostRequest("변경-제목", "변경-내용");
        PostDto.CommonResponse response = new PostDto.CommonResponse(postId, "변경-제목", "변경-내용", 1L, "신범철", LocalDateTime.now());

        when(postService.update(eq(postId), any())).thenReturn(response);

        //when & then
        this.mockMvc.perform(put("/posts/{id}", response.id())
                        .content(objectMapper.writeValueAsString(updatePostRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.STRING).description("상태코드"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("글 아이디"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("작성자 아이디"),
                                fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("생성자 이름"),
                                fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시간")
                        )
                ));
    }
}
