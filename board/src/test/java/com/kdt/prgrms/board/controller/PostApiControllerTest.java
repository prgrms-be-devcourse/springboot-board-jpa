package com.kdt.prgrms.board.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.prgrms.board.domain.Post;
import com.kdt.prgrms.board.dto.PostAddRequest;
import com.kdt.prgrms.board.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostApiController.class)
public class PostApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @Nested
    @DisplayName("addPost메서드는")
    class DescribeAddPost {

        private final String url = "/api/v1/posts";

        @Nested
        @DisplayName("제목이 빈 게시글 생성요청이 들어오면")
        class ContextNotExistTitlePostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("", "Hello");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("제목이 Null인 게시글 생성인청이 들어오면")
        class ContextNullTitlePostAddRequest {

            PostAddRequest requestBody = new PostAddRequest(null, "Hello");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("제목이 공백인 게시글 생성인청이 들어오면")
        class ContextWhiteSpaceTitlePostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("  ", "Hello");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("내용이 없는 게시글 생성요청이 들어오면")
        class ContextNotExistContentPostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("Title", "");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("내용이 null인 게시글 생성요청이 들어오면")
        class ContextNullContentPostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("Tilte", null);

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("내용이 없는 게시글 생성요청이 들어오면")
        class ContextWhiteSpaceContentPostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("Title", "   ");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("400 bad request 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("제목과 내용이 있는 게시글 생성요청이 들어오면")
        class ContextPostAddRequest {

            PostAddRequest requestBody = new PostAddRequest("Title", "Hello");

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(url)
                    .content(toJson(requestBody))
                    .contentType(MediaType.APPLICATION_JSON);

            @Test
            @DisplayName("201 create 응답을 반환한다.")
            void itReturnOk() throws Exception {

                mockMvc.perform(request)
                        .andExpect(status().isCreated());
            }

            @Test
            @DisplayName("service의 addPost메서드를 호출한다.")
            void CallServiceAddPost() throws Exception {

                mockMvc.perform(request);

                verify(postService).addPost(any(Post.class));
            }
        }
    }

    private String toJson(Object obj) {

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
