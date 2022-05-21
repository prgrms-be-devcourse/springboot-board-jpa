package com.waterfogsw.springbootboardjpa.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.springbootboardjpa.post.service.PostService;
import com.waterfogsw.springbootboardjpa.post.util.PostConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostApiController.class)
@MockBeans({
        @MockBean(JpaMetamodelMappingContext.class),
        @MockBean(PostConverter.class)
})
class PostApiControllerTest {
    private static final String URL = "/api/v1/posts";

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName("addPost 메서드는")
    class Describe_addPost {

        @Nested
        @DisplayName("title, content 가 빈문자열이 아니고, userId 가 양수이면")
        class Context_with_ValidData {

            @Test
            @DisplayName("created 응답을 반환한다")
            void It_ResponseCreated() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isCreated());
                verify(postService).addPost(anyLong(), any());
            }
        }

        @Nested
        @DisplayName("title 의 길이가 100자 이상이면")
        class Context_with_OverHundredsCharactersTitle {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void It_ResponseBadRequest() throws Exception {
                final var testTitle = "t".repeat(101);

                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", testTitle);
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }


        @Nested
        @DisplayName("title 이 빈 문자열이거나, 존재하지 않으면")
        class Context_with_BlankTitle {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_ResponseBadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", src);
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("content 가 빈 문자열이거나, 존재하지 않으면")
        class Context_with_BlankContent {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_responseBadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", src);
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("userId 가 양수가 아니면")
        class Context_with_NotPositiveUserId {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("BadRequest 를 응답한다")
            void It_responseBadRequest(long src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "test");
                requestMap.put("userId", src);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("userId 가 없으면")
        class Context_with_NullUserId {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void It_responseBadRequest() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "test");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("updatePost 메서드는")
    class Describe_updatePost {

        final Long testPostId = 1L;

        @Nested
        @DisplayName("모든값이 유효하면")
        class Context_with_ValidData {

            @Test
            @DisplayName("ok 응답을 반환한다")
            void It_ResponseCreated() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + testPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isOk());
                verify(postService).updatePost(anyLong(), anyLong(), any());
            }
        }

        @Nested
        @DisplayName("id 값이 양수가 아니면")
        class Context_with_NotPositiveId {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("BadRequest 를 응답한다")
            void It_ResponseBadRequest(long src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + src)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }


        @Nested
        @DisplayName("title 의 길이가 100자 이상이면")
        class Context_with_OverHundredsCharactersTitle {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void It_ResponseBadRequest() throws Exception {
                final var testTitle = "t".repeat(101);

                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", testTitle);
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + testPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }


        @Nested
        @DisplayName("title 이 빈 문자열이거나, 존재하지 않으면")
        class Context_with_BlankTitle {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_ResponseBadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", src);
                requestMap.put("content", "testContent");
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + testPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("content 가 빈 문자열이거나, 존재하지 않으면")
        class Context_with_BlankContent {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_responseBadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", src);
                requestMap.put("userId", 1);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + testPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("userId 가 양수가 아니면")
        class Context_with_NotPositiveUserId {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("BadRequest 를 응답한다")
            void It_responseBadRequest(long src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("title", "test");
                requestMap.put("content", "test");
                requestMap.put("userId", src);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.put(URL + "/" + testPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content);

                final var response = mockMvc.perform(request);
                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("getOne 메서드는")
    class Describe_GetOne {

        @Nested
        @DisplayName("id 값이 양수이면")
        class Context_with_PositiveId {

            final Long testGetId = 1L;

            @Test
            @DisplayName("ok 응답을 반환한다")
            void It_ResponseOk() throws Exception {
                final var request = MockMvcRequestBuilders.get(URL + "/" + testGetId);

                final var response = mockMvc.perform(request);

                response.andExpect(status().isOk());
                verify(postService).getOne(anyLong());
            }
        }

        @Nested
        @DisplayName("id 값이 양수가 아니면")
        class Context_with_NotPositiveId {

            @ParameterizedTest
            @ValueSource(longs = {-1, 0})
            @DisplayName("ok 응답을 반환한다")
            void It_ResponseOk(long src) throws Exception {
                final var request = MockMvcRequestBuilders.get(URL + "/" + src);

                final var response = mockMvc.perform(request);

                response.andExpect(status().isBadRequest());
            }
        }
    }

    @Nested
    @DisplayName("getAll 메서드는")
    class Describe_getAll {

        @Nested
        @DisplayName("요청되면")
        class Context_with_Requested {

            @Test
            @DisplayName("ok 응답을 반환한다")
            void It_ResponseOk() throws Exception {
                final var request = MockMvcRequestBuilders.get(URL);

                final var response = mockMvc.perform(request);

                response.andExpect(status().isOk());
                verify(postService).getAll();
            }
        }
    }
}
