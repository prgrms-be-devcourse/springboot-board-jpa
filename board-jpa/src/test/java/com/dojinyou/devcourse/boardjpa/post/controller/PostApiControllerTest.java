package com.dojinyou.devcourse.boardjpa.post.controller;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.post.service.PostService;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    private final String testTitle = "test title";
    private final String testContent = "test content";

    @Nested
    class 게시물_생성_요청시 {

        @Nested
        class 비정상적인_생성_요청이_넘어올_경우 {

            @Test
            void Bad_Request로_응답한다_User_id_누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);

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
                requestBody.put("content", testContent);

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
                requestBody.put("title", testTitle);

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
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isCreated());
                verify(postService, atLeastOnce()).create(eq(createUserId), any());
            }
        }
    }

    @Nested
    class 아이디를_이용한_게시물_조회시 {

        @Nested
        class 아이디_값이_비정상적일_경우 {
            
            @ParameterizedTest
            @ValueSource(strings = {"-1", "0"})
            void Bad_Request로_응답한다_아이디값_범위_밖(String inputId) throws Exception {
                when(postService.findById(Long.parseLong(inputId))).thenThrow(new IllegalArgumentException());

                final var response = mockMvc.perform(get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isBadRequest());
                verify(postService, atMostOnce()).findById(anyLong());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하지_않을_경우 {

            @Test
            void Not_Found로_응답한다() throws Exception {
                String inputId = "10";
                when(postService.findById(Long.parseLong(inputId))).thenThrow(new NotFoundException());

                final var response = mockMvc.perform(get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하는_경우 {

            @Test
            void Created로_응답한다() throws Exception {
                long inputId = 1L;
                LocalDateTime localDateTime = LocalDateTime.of(2022, 5, 24,
                                                               10, 38, 0
                                                              );
                PostResponseDto postResponseDto = new PostResponseDto.Builder().id(inputId)
                                                                               .title(testTitle)
                                                                               .content(testContent)
                                                                               .createdAt(localDateTime)
                                                                               .build();
                when(postService.findById(inputId)).thenReturn(postResponseDto);

                final var response = mockMvc.perform(
                        get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isOk());

                verify(postService, atLeastOnce()).findById(inputId);
            }
        }
    }

    private String toJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
