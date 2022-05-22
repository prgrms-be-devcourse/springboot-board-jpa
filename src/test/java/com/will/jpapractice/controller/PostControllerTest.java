package com.will.jpapractice.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.will.jpapractice.domain.post.application.PostService;
import com.will.jpapractice.domain.post.dto.PostRequest;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.domain.user.domain.User;
import com.will.jpapractice.domain.user.dto.UserResponse;
import com.will.jpapractice.domain.user.repository.UserRepository;
import com.will.jpapractice.global.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private Converter converter;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("포스트가 하나도 없을 경우 data 값은 비어있다.")
    void test_find_post_if_empty() throws Exception {
        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(10))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("data").isEmpty());
    }

    @Test
    @DisplayName("포스트 저장 시 인증 헤더 값을 미지정하면 500 에러를 반환한다.")
    void test_create_post_if_empty_header_of_authentication() throws Exception {
        PostRequest postRequest = new PostRequest("this is title", "this is content");

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest))
                )
                .andExpect(status().is5xxServerError())
                .andDo(print());

    }

    @Test
    @DisplayName("게시글을 생성한다.")
    void test_create_post() throws Exception {
        PostRequest postRequest = new PostRequest("this is title", "this is content");
        User user = mock(User.class);

        when(postService.save(1L, postRequest)).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(1L);
        when(converter.toUserResponse(user)).thenReturn(new UserResponse(1L, "will", 22));

        mockMvc.perform(post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest))
                        .header(AUTHORIZATION, "1")
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestHeaders(
                                        headerWithName("AUTHORIZATION").description("작성자 ID")
                                ),
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(STRING).description("게시글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data").type(NUMBER).description("게시글 ID")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시글을 수정한다.")
    void test_update_post() throws Exception {
        PostRequest postRequest = new PostRequest("this is updated title", "this is updated content");

        when(postService.update(1L, postRequest)).thenReturn(1L);

        mockMvc.perform(put("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("title").type(STRING).description("게시글 제목"),
                                        fieldWithPath("content").type(STRING).description("게시글 내용")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data").type(NUMBER).description("게시글 ID")
                                )
                        )
                );
    }

    @Test
    @DisplayName("모든 포스트를 페이징 조회한다.")
    void test_paging_all_post() throws Exception {
        List<PostResponse> postResponses = new ArrayList<>();
        for (long i=0; i<5; i++) {
            postResponses.add(
                    PostResponse.builder()
                            .id(i)
                            .title("this is title - " + i)
                            .content("thie is content - " + i)
                            .userId(1L)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        }

        Page<PostResponse> postResponsePage = new PageImpl<>(postResponses);
        when(postService.findPosts(PageRequest.of(1, 5)))
                .thenReturn(postResponsePage);

        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("page", "1")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-all-posts",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestParameters(
                                        parameterWithName("page").description("페이지 번호"),
                                        parameterWithName("size").description("페이지 크기")
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data.content[].id").type(NUMBER).description("게시글 ID"),
                                        fieldWithPath("data.content[].title").type(STRING).description("게시글 제목"),
                                        fieldWithPath("data.content[].content").type(STRING).description("게시글 내용"),
                                        fieldWithPath("data.content[].userId").type(NUMBER).description("유저 정보"),
                                        fieldWithPath("data.content[].createdAt").type(STRING).description("게시글 생성시간"),
                                        fieldWithPath("data.content[].updatedAt").type(STRING).description("게시글 수정시간"),
                                        fieldWithPath("data.pageable").type(STRING).description("페이지"),
                                        fieldWithPath("data.last").type(BOOLEAN).description("마지막 페이지 여부"),
                                        fieldWithPath("data.totalPages").type(NUMBER).description("전체 페이지 수"),
                                        fieldWithPath("data.totalElements").type(NUMBER).description("전체 게시글 수"),
                                        fieldWithPath("data.size").type(NUMBER).description("페이지 크기"),
                                        fieldWithPath("data.number").type(NUMBER).description("페이지 번호"),
                                        fieldWithPath("data.sort.empty").type(BOOLEAN).description("정렬 설정 여부"),
                                        fieldWithPath("data.sort.sorted").type(BOOLEAN).description("정렬 여부"),
                                        fieldWithPath("data.sort.unsorted").type(BOOLEAN).description("비정렬 여부"),
                                        fieldWithPath("data.first").type(BOOLEAN).description("첫 페이지 여부"),
                                        fieldWithPath("data.numberOfElements").type(NUMBER).description("게시글 개수"),
                                        fieldWithPath("data.empty").type(BOOLEAN).description("빈 값 여부")
                                )
                        )
                );
    }


    @Test
    @DisplayName("특정 포스트를 조회한다.")
    void test_get_post() throws Exception {
        PostResponse response = PostResponse.builder()
                .id(1L)
                .title("this is title")
                .content("thie is content")
                .userId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(postService.findPost(1L)).thenReturn(response);

        mockMvc.perform(get("/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("find-one-post",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("statusCode").type(NUMBER).description("응답 상태 코드"),
                                        fieldWithPath("data.id").type(NUMBER).description("게시글 ID"),
                                        fieldWithPath("data.title").type(STRING).description("게시글 제목"),
                                        fieldWithPath("data.content").type(STRING).description("게시글 내용"),
                                        fieldWithPath("data.userId").type(NUMBER).description("유저 정보"),
                                        fieldWithPath("data.createdAt").type(STRING).description("게시글 생성 시간"),
                                        fieldWithPath("data.updatedAt").type(STRING).description("게시글 수정 시간")
                                )
                        )
                );
    }

}
