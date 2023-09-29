package com.blackdog.springbootBoardJpa.post.controller;

import com.blackdog.springbootBoardJpa.domain.post.controller.PostController;
import com.blackdog.springbootBoardJpa.domain.post.controller.converter.PostControllerConverter;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostCreateDto;
import com.blackdog.springbootBoardJpa.domain.post.service.PostService;
import com.blackdog.springbootBoardJpa.domain.post.service.converter.PostServiceConverter;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostCreateRequest;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostUpdateRequest;
import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@WebMvcTest(PostController.class)
@Import({PostServiceConverter.class, PostControllerConverter.class, PostService.class})
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostControllerConverter ControllerConverter;

    @Autowired
    PostServiceConverter serviceConverter;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService service;

    @ParameterizedTest
    @DisplayName("존재하는 유저로 게시글을 생성하면 성공한다.")
    @MethodSource("provideTestData")
    void savePost_Dto_SaveReturnResponse(PostCreateDto dto, PostResponse response) throws Exception {
        // given
        given(service.savePost(any(Long.class), any(PostCreateRequest.class)))
                .willReturn(response);

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.post("/posts/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(dto.title()))
                .andDo(document("post-save",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("유저 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시물 작성 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시물 수정 시간")
                        )
                ));

        // then
        verify(service, times(1)).savePost(any(), any());
    }

    @Test
    @DisplayName("Dto의 필드가 valid하지 않다면 유효성 검사에 실패한다.")
    void savePost_Dto_ThrowMethodArgumentNotValidException() throws Exception {
        PostCreateDto dto = new PostCreateDto("", "내용");
        MockHttpServletRequestBuilder builder = post("/posts/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(dto));

        MvcResult result = mockMvc.perform(builder)
                .andDo(print())
                .andReturn();

        String message = result.getResponse().getContentAsString(StandardCharsets.UTF_8);

        Assertions.assertThat(message).contains("Method Argument가 적절하지 않습니다.");

    }


    @ParameterizedTest
    @DisplayName("존재하는 게시글을 수정하면 성공한다.")
    @MethodSource("provideTestData")
    void updatePost_Dto_UpdateReturnResponse(PostCreateDto dto, PostResponse response) throws Exception {
        // given
        given(service.updatePost(any(Long.class), any(Long.class), any(PostUpdateRequest.class)))
                .willReturn(response);

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.patch("/posts/{postId}/user/{userId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(dto.title()))
                .andDo(document("post-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디"),
                                parameterWithName("userId").description("유저 아이디")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시물 작성 시간"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시물 수정 시간")
                        )
                ));

        // then
        verify(service, times(1)).updatePost(any(), any(), any());
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 삭제하면 성공한다.")
    @MethodSource("provideTestData")
    void deletePostById_Dto_Delete() throws Exception {
        // given
        doNothing().when(service)
                .deletePostById(any(Long.class), any(Long.class));

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/posts/{postId}/user/{userId}", 1L, 1L))
                .andExpect(status().isOk())
                .andDo(document("post - delete",
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디"),
                                parameterWithName("userId").description("회원 아이디")
                        )
                ));

        // then
        verify(service, times(1)).deletePostById(any(), any());
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 조회하면 성공한다.")
    @MethodSource("provideTestData")
    void getPostById_id_ReturnResponse(PostCreateDto dto, PostResponse response) throws Exception {
        // given
        given(service.findPostById(any(Long.class))).willReturn(response);

        // when
        mockMvc.perform(RestDocumentationRequestBuilders.get("/posts/{postId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.title").value(dto.title()))
                .andDo(print())
                .andDo(document("post-get",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("postId").description("게시글 아이디")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시글 본문"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("createdAt").type(JsonFieldType.STRING).description("게시글 작성일"),
                                fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("게시글 갱신일")
                        )
                ));

        // then
        verify(service, times(1)).findPostById(any());
    }

    @ParameterizedTest
    @DisplayName("모든 게시글을 조회하면 성공한다.")
    @MethodSource("provideTestData")
    void getAllPosts_Void_ReturnResponses() throws Exception {
        // given
        given(service.findAllPosts(pageable)).willReturn(responses);

        // when
        mockMvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.postResponses.content", Matchers.hasSize(2)))
                .andDo(print())
                .andDo(document("posts - get", preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("postResponses").type(JsonFieldType.OBJECT).description("게시물 응답"),
                                fieldWithPath("postResponses.content").type(JsonFieldType.ARRAY).description("게시물 정보 배열"),
                                fieldWithPath("postResponses.content[].id").type(JsonFieldType.NUMBER).description("게시물 ID"),
                                fieldWithPath("postResponses.content[].title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("postResponses.content[].content").type(JsonFieldType.STRING).description("게시물 내용"),
                                fieldWithPath("postResponses.content[].name").type(JsonFieldType.STRING).description("게시물 작성자 이름"),
                                fieldWithPath("postResponses.content[].createdAt").type(JsonFieldType.STRING).description("게시물 생성일"),
                                fieldWithPath("postResponses.content[].updatedAt").type(JsonFieldType.STRING).description("게시물 갱신일"),

                                fieldWithPath("postResponses.pageable").type(JsonFieldType.OBJECT).description("pageable").ignored(),
                                fieldWithPath("postResponses.last").type(JsonFieldType.BOOLEAN).description("last").ignored(),
                                fieldWithPath("postResponses.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("postResponses.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("postResponses.size").type(JsonFieldType.NUMBER).description("size").ignored(),
                                fieldWithPath("postResponses.number").type(JsonFieldType.NUMBER).description("number").ignored(),
                                fieldWithPath("postResponses.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty").ignored(),
                                fieldWithPath("postResponses.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted").ignored(),
                                fieldWithPath("postResponses.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted").ignored(),
                                fieldWithPath("postResponses.first").type(JsonFieldType.BOOLEAN).description("first").ignored(),
                                fieldWithPath("postResponses.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements").ignored(),
                                fieldWithPath("postResponses.empty").type(JsonFieldType.BOOLEAN).description("empty").ignored()
                        )));

        // then
        verify(service, times(1)).findAllPosts(pageable);
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 유저로 조회하면 성공한다.")
    @MethodSource("provideTestData")
    void getPostsByUserId_id_ReturnResponse(PostCreateDto dto, PostResponse response) throws Exception {
        // given
        given(service.findPostsByUserId(any(), any())).willReturn(responses);

        // when
        mockMvc.perform(get("/posts")
                        .param("page", "0")
                        .param("size", "5")
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.postResponses.content", Matchers.hasSize(2)))
                .andDo(print())
                .andDo(document("post-get-by-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        queryParameters(
                                parameterWithName("page").description("페이지"),
                                parameterWithName("size").description("사이즈"),
                                parameterWithName("userId").description("회원ID")
                        ),
                        responseFields(
                                fieldWithPath("postResponses").type(JsonFieldType.OBJECT).description("게시글 응답"),
                                fieldWithPath("postResponses.content").type(JsonFieldType.ARRAY).description("게시글 정보 배열"),
                                fieldWithPath("postResponses.content[].id").type(JsonFieldType.NUMBER).description("게시글 아이디"),
                                fieldWithPath("postResponses.content[].title").type(JsonFieldType.STRING).description("게시글 이름"),
                                fieldWithPath("postResponses.content[].content").type(JsonFieldType.STRING).description("게시글 나이"),
                                fieldWithPath("postResponses.content[].name").type(JsonFieldType.STRING).description("게시글 작성자 이름"),
                                fieldWithPath("postResponses.content[].createdAt").type(JsonFieldType.STRING).description("게시글 생성일"),
                                fieldWithPath("postResponses.content[].updatedAt").type(JsonFieldType.STRING).description("게시글 갱신일"),

                                fieldWithPath("postResponses.pageable").type(JsonFieldType.OBJECT).description("pageable").ignored(),
                                fieldWithPath("postResponses.last").type(JsonFieldType.BOOLEAN).description("last").ignored(),
                                fieldWithPath("postResponses.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("postResponses.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("postResponses.size").type(JsonFieldType.NUMBER).description("size").ignored(),
                                fieldWithPath("postResponses.number").type(JsonFieldType.NUMBER).description("number").ignored(),
                                fieldWithPath("postResponses.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty").ignored(),
                                fieldWithPath("postResponses.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted").ignored(),
                                fieldWithPath("postResponses.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted").ignored(),
                                fieldWithPath("postResponses.first").type(JsonFieldType.BOOLEAN).description("first").ignored(),
                                fieldWithPath("postResponses.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements").ignored(),
                                fieldWithPath("postResponses.empty").type(JsonFieldType.BOOLEAN).description("empty").ignored()
                        )
                ));

        // then
        verify(service, times(1)).findPostsByUserId(any(), any());
    }

    static User user = User.builder()
            .name(new Name("둘리"))
            .age(new Age(1200))
            .hobby("고길동 등골 빼먹기")
            .build();

    static List<PostCreateDto> postCreateDto = List.of(
            new PostCreateDto("subject1", "content1"),
            new PostCreateDto("subject2", "content2")
    );

    static List<PostResponse> postResponses = List.of(
            new PostResponse(1L, "subject1", "content1", user.getName().getNameValue(), LocalDateTime.now(), LocalDateTime.now()),
            new PostResponse(2L, "subject2", "content2", user.getName().getNameValue(), LocalDateTime.now(), LocalDateTime.now())
    );

    static Pageable pageable = PageRequest.of(0, 5);

    static PostResponses responses = new PostResponses(
            new PageImpl<>(postResponses)
    );

    static Stream<Arguments> provideTestData() {
        return IntStream.range(0, 2)
                .mapToObj(i -> Arguments.of(postCreateDto.get(i), postResponses.get(i)));
    }
}
