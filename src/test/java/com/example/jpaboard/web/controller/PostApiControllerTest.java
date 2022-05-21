package com.example.jpaboard.web.controller;

import com.example.jpaboard.config.ServiceConfiguration;
import com.example.jpaboard.exception.CustomException;
import com.example.jpaboard.service.dto.post.PostSaveRequest;
import com.example.jpaboard.service.dto.post.PostResponse;
import com.example.jpaboard.service.post.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import java.util.stream.Stream;
import static com.example.jpaboard.exception.ErrorCode.*;
import static java.time.LocalDateTime.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(value = {PostApiController.class, ServiceConfiguration.class})
class PostApiControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Nested
    class findAll메서드는 {
        @Test
        @DisplayName("전체 게시글을 조회한다")
        void 전체_게시글을_조회한다() throws Exception {
            // given
            List<PostResponse> postResponses = Arrays.asList(new PostResponse(1L, "테스트", "테스트입니다.", "작성자 이름", now()));
            given(postService.findAll(any(Pageable.class)))
                             .willReturn(postResponses);

            // when, then
            mockMvc.perform(get("/api/v1/posts"))
                   .andExpect(status().isOk())
                   .andExpect(content().json(toJson(postResponses)));
        }

        @Nested
        class 조회_결과가_없다면 {
            @Test
            @DisplayName("빈 리스트를 반환한다")
            void 빈_리스트를_반환한다() throws Exception {
                // given
                given(postService.findAll(any(Pageable.class)))
                                 .willReturn(Collections.EMPTY_LIST);

                // when, then
                mockMvc.perform(get("/api/v1/posts"))
                       .andExpect(status().isOk())
                       .andExpect(content().json(toJson(Collections.EMPTY_LIST)));
            }
        }
    }

    @Nested
    class findById메서드는 {
        @Test
        @DisplayName("아이디로 게시글 한 건을 조회한다")
        void 아이디로_게시글_한_건을_조회한다() throws Exception {
            // given
            Long id = 1L;
            PostResponse postResponse = new PostResponse(id, "제목", "내용", "작성자", now());
            given(postService.findById(id))
                             .willReturn(postResponse);

            // when, then
            mockMvc.perform(get("/api/v1/posts/" + id))
                   .andExpect(status().isOk())
                   .andExpect(content().json(toJson(postResponse)));
        }

        @Nested
        class 게시글이_존재하지_않는다면 {
            @Test
            @DisplayName("NotFound 응답을 보낸다")
            void NotFound_응답을_보낸다() throws Exception {
                // given
                Long id = 1L;
                given(postService.findById(anyLong()))
                                 .willThrow(new CustomException(POST_NOT_FOUND));

                // when, then
                mockMvc.perform(get("/api/v1/posts/" + id))
                       .andExpect(status().isNotFound());
            }
        }
    }

    @Nested
    class save메서드는 {
        @Test
        @DisplayName("게시글을 저장한다")
        void 게시글을_저장한다() throws Exception {
            //given
            PostSaveRequest request = new PostSaveRequest(1L, "제목", "내용");

            //when, then
            mockMvc.perform(post("/api/v1/posts")
                   .contentType(MediaType.APPLICATION_JSON)
                   .content((toJson(request))))
                   .andExpect(status().isOk());
        }

        @Nested
        class 존재하지_않는_사용자라면 {
            @Test
            @DisplayName("NotFound 응답을 보낸다")
            void NotFound_응답을_보낸다() throws Exception {
                //given
                PostSaveRequest request = new PostSaveRequest(1L, "제목", "내용");
                willThrow(new CustomException(USER_NOT_FOUND))
                        .given(postService).save(anyLong(), anyString(), anyString());

                //when, then
                mockMvc.perform(post("/api/v1/posts/")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content((toJson(request))))
                       .andExpect(status().isNotFound());
            }
        }

        @Nested
        class 아이디가_없거나_숫자_문자열이_아니라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @ValueSource(strings = {" ", "$", "string"})
            void BadRequest_응답을_보낸다(String userId) throws Exception {
                //given
                Map<String, String> request = Map.of(
                        "userId", userId,
                        "title", "제목",
                        "content", "내용");

                //when, then
                mockMvc.perform(post("/api/v1/posts/")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content((toJson(request))))
                       .andExpect(status().isBadRequest());
            }
        }

        @Nested
        class 제목이나_내용이_null이거나_빈_값이거나_공백이라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @MethodSource("com.example.jpaboard.web.controller.MethodSourceProvider#provideStrings")
            void BadRequest_응답을_보낸다(String title, String content) throws Exception {
                //given
                PostSaveRequest request = new PostSaveRequest(1L, title, content);

                //when, then
                mockMvc.perform(post("/api/v1/posts")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content((toJson(request))))
                       .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    class update메서드는 {
        @Test
        @DisplayName("게시글을 수정하고 수정된 게시글을 반환한다")
        void 게시글을_수정하고_수정된_게시글을_반환한다() throws Exception {
            //given
            Long id = 1L;
            Long userId = 1L;
            String updateTitle = "제목";
            String updateContent = "내용";
            PostResponse response = new PostResponse(id, updateTitle, updateContent, "작성자", now());
            given(postService.update(anyLong(), anyLong(), nullable(String.class), nullable(String.class)))
                             .willReturn(response);

            //when, then
            mockMvc.perform(patch("/api/v1/posts/" + id)
                   .contentType(APPLICATION_FORM_URLENCODED)
                   .content("userId=" + userId + "&title=" + updateTitle + "&content=" + updateContent))
                   .andExpect(status().isOk())
                   .andExpect(content().json(toJson(response)));
        }

        @Nested
        class 사용자와_게시글의_작성자가_다르다면 {
            @Test
            @DisplayName("Forbidden 응답을 보낸다")
            void Forbidden_응답을_보낸다() throws Exception {
                //given
                Long id = 1L;
                Long userId = 1L;
                String updateTitle = "제목";
                String updateContent = "내용";
                given(postService.update(anyLong(), anyLong(), nullable(String.class), nullable(String.class)))
                                 .willThrow(new CustomException(FORBIDDEN_USER));

                //when, then
                mockMvc.perform(patch("/api/v1/posts/" + id)
                       .contentType(APPLICATION_FORM_URLENCODED)
                       .content("userId=" + userId + "&title=" + updateTitle + "&content=" + updateContent))
                       .andExpect(status().isForbidden());
            }
        }

        @Nested
        class 게시글_아이디가_없거나_숫자_문자열이_아니라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @ValueSource(strings = {" ", "$", "string"})
            void BadRequest_응답을_보낸다(String id) throws Exception {
                //given
                //when, then
                mockMvc.perform(patch("/api/v1/posts/" + id))
                       .andExpect(status().isBadRequest());
            }
        }

        @Nested
        class 사용자_아이디가_없거나_숫자_문자열이_아니라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @ValueSource(strings = {" ", "$", "string"})
            void BadRequest_응답을_보낸다(String userId) throws Exception {
                //given
                Long id = 1L;
                String updateTitle = "제목";
                String updateContent = "내용";

                //when, then
                mockMvc.perform(patch("/api/v1/posts/" + id)
                       .contentType(APPLICATION_FORM_URLENCODED)
                       .content("userId=" + userId + "&title=" + updateTitle + "&content=" + updateContent))
                       .andExpect(status().isBadRequest());
            }
        }

        @Nested
        class 제목이나_내용이_공백이거나_빈_값이라면 {
            @DisplayName("BadRequest 응답을 보낸다")
            @ParameterizedTest
            @MethodSource("com.example.jpaboard.web.controller.MethodSourceProvider#provideStrings")
            void BadRequest_응답을_보낸다(String title, String content) throws Exception {
                //given
                Long id = 1L;
                Long userId = 1L;

                //when, then
                mockMvc.perform(patch("/api/v1/posts/" + id)
                       .contentType(APPLICATION_FORM_URLENCODED)
                       .content("userId=" + userId + "&title=" + title + "&content=" + content))
                       .andExpect(status().isBadRequest());
            }
        }
    }

    private String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

class MethodSourceProvider {
    public static Stream<Arguments> provideStrings() { // argument source method
        return Stream.of(
                Arguments.of("", "내용"),
                Arguments.of("", null),
                Arguments.of(" ", "내용"),
                Arguments.of(" ", null),
                Arguments.of("제목", ""),
                Arguments.of(null, ""),
                Arguments.of("제목", " "),
                Arguments.of(null, " ")
        );
    }
}
