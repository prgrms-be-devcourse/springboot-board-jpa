package org.prgrms.myboard.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.prgrms.myboard.domain.CursorResult;
import org.prgrms.myboard.domain.OffsetResult;
import org.prgrms.myboard.dto.PostCreateRequestDto;
import org.prgrms.myboard.dto.PostResponseDto;
import org.prgrms.myboard.dto.PostUpdateRequestDto;
import org.prgrms.myboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(PostRestController.class)
public class RestdocsTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    ObjectMapper objectMapper;

    PostResponseDto postResponseDtoOne;
    PostResponseDto postResponseDtoTwo;
    @BeforeAll
    void setup() {
        postResponseDtoOne = new PostResponseDto(1L, "testTitle1", "testContent1", "testUser", LocalDateTime.now(), LocalDateTime.now());
        postResponseDtoTwo = new PostResponseDto(2L, "testTitle2", "testContent2", "testUser", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    @DisplayName("게시글 생성 요청관련 restdocs를 생성한다.")
    void save_test() throws Exception {
        // given
        PostCreateRequestDto postDto = new PostCreateRequestDto("testTitle", "testContent", 1L);
        when(postService.createPost(any())).thenReturn(postResponseDtoOne);

        // when then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-save",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("userId").type(JsonFieldType.NUMBER).description("UserId")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }

    @Test
    @DisplayName("게시글 id로 조회관련 restdocs를 생성한다.")
    void find_post_by_id_test() throws Exception{
        // given
        when(postService.findById(anyLong())).thenReturn(postResponseDtoOne);

        // when // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-findById",
                pathParameters(
                    parameterWithName("postId").description("Id of Post to find")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }

    @DisplayName("게시글 업데이트 관련 restdocs를 생성한다.")
    @Test
    void updated_by_id_test() throws Exception {
        // given
        PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto("testUpdateTitle", "testUpdateContent");

        // when then
        when(postService.updateById(anyLong(), any())).thenReturn(postResponseDtoOne);
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequestDto)))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-update",
                pathParameters(
                    parameterWithName("postId").description("Id of Post to update")
                ),
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }

    @DisplayName("offset기반 페이지네이션 관련 restdocs를 생성한다.")
    @Test
    void offset_pagination_test() throws Exception{
        // given
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        postResponseDtos.add(0, postResponseDtoOne);
        postResponseDtos.add(1, postResponseDtoTwo);

        OffsetResult<PostResponseDto> offsetResult = new OffsetResult<>(1, 1, 2, postResponseDtos);

        // when
        when(postService.findPostsByOffsetPagination(anyInt(), anyInt())).thenReturn(offsetResult);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts?page={page}&pageSize={pageSize}", 1, 10))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-offsetPagination",
                RequestDocumentation.queryParameters(
                    parameterWithName("page").description("Index of Page"),
                    parameterWithName("pageSize").description("Size of Page")
                ),
                responseFields(
                    fieldWithPath("currentPage").type(JsonFieldType.NUMBER).description("CurrentPage"),
                    fieldWithPath("lastPageIndex").type(JsonFieldType.NUMBER).description("LastPageIndex"),
                    fieldWithPath("postCount").type(JsonFieldType.NUMBER).description("PostCount"),
                    fieldWithPath("values[]").type(JsonFieldType.ARRAY).description("Posts-Array"),
                    fieldWithPath("values[].id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("values[].title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("values[].content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("values[].createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("values[].createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("values[].updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }

    @DisplayName("cursor기반 페이지네이션 관련 restdocs를 생성한다.")
    @Test
    void cursor_pagination_test() throws Exception{
        // given
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        postResponseDtos.add(0, postResponseDtoOne);
        postResponseDtos.add(1, postResponseDtoTwo);

        CursorResult<PostResponseDto> cursorResult = new CursorResult<>(false, false, -1, postResponseDtos, -1L, -1L);

        // when
        when(postService.findPostsByCursorPagination(anyLong(), anyInt())).thenReturn(cursorResult);

        // then
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/cursor?cursorId={cursorId}&pageSize={pageSize}", 1, 10))
            .andExpect(status().isOk()) // 200
            .andDo(print())
            .andDo(document("post-cursorPagination",
                queryParameters(
                    parameterWithName("cursorId").description("Id of Cursor"),
                    parameterWithName("pageSize").description("Size of Page")
                ),
                responseFields(
                    fieldWithPath("hasNext").type(JsonFieldType.BOOLEAN).description("HasNextPage"),
                    fieldWithPath("hasPrevious").type(JsonFieldType.BOOLEAN).description("HasPreviousPage"),
                    fieldWithPath("postCount").type(JsonFieldType.NUMBER).description("PostCount"),
                    fieldWithPath("nextCursorId").type(JsonFieldType.NUMBER).description("NextCursorId"),
                    fieldWithPath("previousCursorId").type(JsonFieldType.NUMBER).description("PreviousCursorId"),
                    fieldWithPath("values[]").type(JsonFieldType.ARRAY).description("Posts-Array"),
                    fieldWithPath("values[].id").type(JsonFieldType.NUMBER).description("PostId"),
                    fieldWithPath("values[].title").type(JsonFieldType.STRING).description("Title"),
                    fieldWithPath("values[].content").type(JsonFieldType.STRING).description("Content"),
                    fieldWithPath("values[].createdBy").type(JsonFieldType.STRING).description("Author"),
                    fieldWithPath("values[].createdAt").type(JsonFieldType.STRING).description("Created Time"),
                    fieldWithPath("values[].updatedAt").type(JsonFieldType.STRING).description("Updated Time")
                )
            ));
    }
}