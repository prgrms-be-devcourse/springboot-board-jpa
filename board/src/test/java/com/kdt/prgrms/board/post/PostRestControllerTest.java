package com.kdt.prgrms.board.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.prgrms.board.controller.PostRestController;
import com.kdt.prgrms.board.dto.PostResponse;
import com.kdt.prgrms.board.dto.PostUpdateRequest;
import com.kdt.prgrms.board.exception.custom.NotFoundException;
import com.kdt.prgrms.board.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PostRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @Nested
    @DisplayName("addPost 메서드는 테스트할 때")
    class DescribeAddPost {

        final String url = "/api/v1/posts";

        final Map<String, Object> validRequest = Map.of(
                "userId", 1,
                "title", "hello",
                "content", "Hi!"
        );

        @Nested
        @DisplayName("userId가 음수인 요청이 들어오면")
        class ContextNegativeUserId {

            @Test
            @DisplayName("badRequest 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);
                requestMap.put("userId", -1);

                final MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("제목이 빈문자열, null, 공백인 요청이 들어오면")
        class ContextWrongTitle {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"\n", "\t"})
            @DisplayName("badRequest 응답을 반환한다.")
            void itReturnBadRequest(String title) throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);
                requestMap.put("title", title);

                final MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("내용이 빈문자열, null, 공백인 요청이 들어오면")
        class ContextWrongContent {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"\n", "\t"})
            @DisplayName("badRequest 응답을 반환한다.")
            void itReturnBadRequest(String content) throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);
                requestMap.put("content", content);

                final MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("getPosts 메서드는 테스트 할 때")
    class DescribeGetPosts {

        final String url = "/api/v1/posts";

        final List<PostResponse> response = List.of(new PostResponse(1, "jahee", 1, "title", "content"));

        @Nested
        @DisplayName("get요청을 받으면")
        class ContextReceiveGetRequest {

            @Test
            @DisplayName("Post를 json타입의 리스트로 반환한다..")
            void itReturnPostsByFson() throws Exception {

                when(postService.getPosts()).thenReturn(response);

                mockMvc.perform(get(url))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(toJson(response)));
            }
        }
    }

    @Nested
    @DisplayName("getPostById 메서드는 테스트 할 때")
    class DescribeGetPostById {

        @Nested
        @DisplayName("잘못된 postid를 요청으로 받으면")
        class ContextWrongUserId {

            final long wrongId = -1;

            final String url = "/api/v1/posts/-1";

            @Test
            @DisplayName("NotFound 예외를 반환한다.")
            void itThrowNotFoundException() throws Exception {

                when(postService.getPostById(wrongId)).thenThrow(NotFoundException.class);

                mockMvc.perform(get(url))
                        .andExpect(status().isNotFound());
            }
        }

        @Nested
        @DisplayName("존재하는 postid를 요청으로 받으면")
        class ContextGetRequestUserId {

            long id = 1;
            final String url = "/api/v1/posts/1";
            final PostResponse response = new PostResponse(1, "jahee", 1, "title", "content");

            @Test
            @DisplayName("해당 Post를 json형태로 반환한다.")
            void itReturnPostResponseByJson() throws Exception {

                when(postService.getPostById(id)).thenReturn(response);

                mockMvc.perform(get(url))
                        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                        .andExpect(content().json(toJson(response)));
            }
        }
    }

    @Nested
    @DisplayName("updatePostById 메서드는 테스트 할 때")
    class DescribeUpdatePostBuId {

        final Map<String, Object> validRequest = Map.of(
                "userId", 1,
                "title", "hello",
                "content", "Hi!"
        );

        @Nested
        @DisplayName("존재하지 않는 id를 put요청 받으면")
        class ContextWrongIdRequest {

            final String url = "/api/v1/posts/-1";

            @Test
            @DisplayName("NotFound 예외를 반환한다.")
            void itThrowNotFoundException() throws Exception {

                final MockHttpServletRequestBuilder request = put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(validRequest));

                doThrow(NotFoundException.class).when(postService)
                        .updatePostById(any(long.class), any(PostUpdateRequest.class));

                mockMvc.perform(request)
                        .andExpect(status().isNotFound());
            }

        }

        @Nested
        @DisplayName("존재하는 postId와 제목이 null, 공백인 요청을 받으면")
        class ContextWrongTitleRequest {

            final String url = "/api/v1/posts/1";

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"\n", "\t"})
            @DisplayName("badRequest 응답을 반환한다.")
            void itReturnBadRequest(String title) throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);
                requestMap.put("title", title);

                final MockHttpServletRequestBuilder request = put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("존재하는 postId와 내용이 null 공백인 요청을 받으면")
        class ContextWrongContentRequest {

            final String url = "/api/v1/posts/1";

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"\n", "\t"})
            @DisplayName("BadRequest 응답을 반환한다.")
            void itReturnBadRequest(String content) throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);
                requestMap.put("content", content);

                final MockHttpServletRequestBuilder request = put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("존재하는 id와 유효한 put 요청 받으면")
        class ContextValidIdRequest {

            final String url = "/api/v1/posts/1";

            @Test
            @DisplayName("Ok 응답을 반환한다.")
            void itReturnOk() throws Exception {

                final Map<String, Object> requestMap = new HashMap<>(validRequest);

                final MockHttpServletRequestBuilder request = put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(requestMap));

                mockMvc.perform(request)
                        .andExpect(status().isOk());
                verify(postService).updatePostById(any(long.class), any(PostUpdateRequest.class));
            }
        }
    }

    private String toJson(Object obj)  {

        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
