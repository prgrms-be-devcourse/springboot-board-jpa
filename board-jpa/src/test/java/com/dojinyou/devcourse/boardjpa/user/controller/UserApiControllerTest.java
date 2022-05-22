package com.dojinyou.devcourse.boardjpa.user.controller;

import com.dojinyou.devcourse.boardjpa.user.service.UserService;
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

@WebMvcTest(UserApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UserApiControllerTest {

    private static final String BASE_URL_PATH = "/api/v1/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Nested
    class 유저_생성_요청시 {

        @Nested
        class 비정상적인_생성_요청이_넘어올_경우 {

            @Test
            void Bad_Request로_응답한다_name누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("age", 10);
                requestBody.put("hobby", "test");

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(userService, never()).create(any());
            }

            @Test
            void Bad_Request로_응답한다_age누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("name", "test");
                requestBody.put("hobby", "test");

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(userService, never()).create(any());
            }
            @Test
            void Bad_Request로_응답한다_hobby누락() throws Exception {
                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("name", "test");
                requestBody.put("age", 10);

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isBadRequest());
                verify(userService, never()).create(any());
            }
        }

        @Nested
        class 정상적인_생성_요청이_넘어올_경우 {

            @Test
            void Created로_응답한다() throws Exception {

                final Map<String, Object> requestBody = new HashMap<>();
                requestBody.put("name", "test");
                requestBody.put("age", 10);
                requestBody.put("hobby", "test");

                final var response = mockMvc.perform(post(BASE_URL_PATH)
                                                             .contentType(MediaType.APPLICATION_JSON)
                                                             .content(toJson(requestBody)));
                response.andExpect(status().isCreated());
                verify(userService, atLeastOnce()).create(any());
            }
        }
    }

    private String toJson(Map<String, Object> object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}