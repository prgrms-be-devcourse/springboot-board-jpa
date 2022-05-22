package com.dojinyou.devcourse.boardjpa.post.controller;

import com.dojinyou.devcourse.boardjpa.post.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostApiControllerTest {

    private static final String BASE_URL_PATH = "/api/v1/posts";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    private final long createUserId = 1L;

    private final String title = "test title";
    private final String content = "test content";

    @Nested
    class 게시물_생성_요청시 {

        @Nested
        class 비정상적인_생성_요청이_넘어올_경우 {

            @Test
            void Bad_Request로_응답한다_User_id_누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("title", title);
                requestBody.put("content", content);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(postService, never()).create(anyLong(), any());
            }

            @Test
            void Bad_Request로_응답한다_Title_누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", createUserId);
                requestBody.put("content", content);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(postService, never()).create(anyLong(), any());
            }
            @Test
            void Bad_Request로_응답한다_Content_누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", createUserId);
                requestBody.put("title", title);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(postService, never()).create(anyLong(), any());
            }
        }

        @Nested
        class 정상적인_생성_요청이_넘어올_경우 {

            @Test
            void Created로_응답한다() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", createUserId);
                requestBody.put("title", title);
                requestBody.put("content", content);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isCreated());
                verify(postService, atLeastOnce()).create(eq(createUserId), any());
            }
        }
    }

    private String toJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}