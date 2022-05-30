package com.dojinyou.devcourse.boardjpa.post.controller;

import com.dojinyou.devcourse.boardjpa.common.exception.NotFoundException;
import com.dojinyou.devcourse.boardjpa.post.entity.Post;
import com.dojinyou.devcourse.boardjpa.post.service.PostDefaultService;
import com.dojinyou.devcourse.boardjpa.post.service.dto.PostResponseDto;
import com.dojinyou.devcourse.boardjpa.user.entity.User;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    PostDefaultService postService;

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

            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(longs = {-1, 0})
            void Bad_Request로_응답한다_아이디값_범위_밖(long inputId) throws Exception {
                when(postService.findById(inputId)).thenThrow(new IllegalArgumentException());

                final var response = mockMvc.perform(get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isBadRequest());
                verify(postService, atMostOnce()).findById(anyLong());
            }

            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(strings = {"test", "invalid123"})
            void Bad_Request로_응답한다_변환불가능(String inputId) throws Exception {

                final var response = mockMvc.perform(get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isBadRequest());
                verify(postService, never()).findById(anyLong());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하지_않을_경우 {

            @Test
            void Not_Found로_응답한다() throws Exception {
                long inputId = 10L;
                given(postService.findById(inputId)).willThrow(new NotFoundException());

                final var response = mockMvc.perform(get(BASE_URL_PATH + "/" + inputId));

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하는_경우 {

            @Test
            void OK로_응답한다() throws Exception {
                long inputId = 1L;
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", inputId);
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);
                User creater = new User.Builder().name("testUser")
                                                 .age(20)
                                                 .hobby("testHobby")
                                                 .build();
                ReflectionTestUtils.setField(creater, "id", 1L);
                Post savedPost = new Post.Builder().title(testTitle)
                                                   .content(testContent)
                                                   .user(creater)
                                                   .build();
                ReflectionTestUtils.setField(savedPost, "id", inputId);
                PostResponseDto responseDto = PostResponseDto.from(savedPost);
                doReturn(responseDto).when(postService).findById(inputId);

                final var response = mockMvc.perform(
                        get(BASE_URL_PATH + "/" + inputId));


                verify(postService, atMostOnce()).findById(inputId);
                response.andExpect(status().isOk());
            }
        }
    }

    @Nested
    class 아이디를_이용한_게시물_수정시 {

        @Nested
        class 아이디_값이_비정상적일_경우 {

            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(longs = {-1, 0})
            void Bad_Request로_응답한다_아이디값_범위_밖(long inputId) throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", createUserId);
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);
                doThrow(new IllegalArgumentException()).when(postService).updateById(anyLong(), any());

                final var response = mockMvc.perform(patch(BASE_URL_PATH + "/" + inputId)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));

                response.andExpect(status().isBadRequest());
                verify(postService, atMostOnce()).updateById(anyLong(), any());
            }

            @ParameterizedTest(name = "{displayName} inputId:{0}")
            @ValueSource(strings = {"test", "invalid123"})
            void Bad_Request로_응답한다_변환불가능(String inputId) throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", createUserId);
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);

                final var response = mockMvc.perform(patch(BASE_URL_PATH + "/" + inputId)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));

                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하지_않을_경우 {

            @Test
            void Not_Found로_응답한다() throws Exception {
                long inputId = 10L;
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", inputId);
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);
                doThrow(new NotFoundException()).when(postService).updateById(anyLong(), any());

                final var response = mockMvc.perform(patch(BASE_URL_PATH + "/" + inputId)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));

                response.andExpect(status().isNotFound());
            }
        }

        @Nested
        class 입력된_아이디_값을_가진_자원이_존재하는_경우 {

            @Test
            void No_Content로_응답한다() throws Exception {
                long inputId = 1L;
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("userId", inputId);
                requestBody.put("title", testTitle);
                requestBody.put("content", testContent);
                User creater = new User.Builder().name("testUser").age(20).hobby("testHobby").build();
                ReflectionTestUtils.setField(creater, "id", 1L);
                Post savedPost = new Post.Builder().title(testTitle)
                                                   .content(testContent)
                                                   .user(creater)
                                                   .build();
                ReflectionTestUtils.setField(savedPost, "id", inputId);

                final var response = mockMvc.perform(
                        patch(BASE_URL_PATH + "/" + inputId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(toJson(requestBody)));

                response.andExpect(status().isNoContent());
            }
        }
    }

    private String toJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
