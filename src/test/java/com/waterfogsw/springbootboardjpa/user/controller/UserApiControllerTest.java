package com.waterfogsw.springbootboardjpa.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waterfogsw.springbootboardjpa.user.service.UserService;
import com.waterfogsw.springbootboardjpa.user.util.UserConverter;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserApiControllerTest {

    private static final String URL = "/api/v1/users";

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Nested
    @DisplayName("addUser 메서드는")
    class Describe_addUser {

        @Nested
        @DisplayName("모든 값이 존재하면")
        class Context_with_validRequest {

            @Test
            @DisplayName("created 를 응답한다")
            void It_response_Created() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", "test");
                requestMap.put("email", "test1234@naver.com");
                requestMap.put("age", 10);
                requestMap.put("hobby", "test");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isCreated());
                verify(userService).addUser(any());
            }
        }

        @Nested
        @DisplayName("이메일, 이름이 존재하면")
        class Context_with_exist_email_name {

            @Test
            @DisplayName("created 를 응답한다")
            void It_response_Created() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", "test");
                requestMap.put("email", "test1234@naver.com");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isCreated());
                verify(userService).addUser(any());
            }
        }

        @Nested
        @DisplayName("이름이 존재하지 않거나, 빈값이면")
        class Context_with_nullName {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_response_BadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", src);
                requestMap.put("email", "test1234@naver.com");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("나이가 음수면")
        class Context_with_negative {

            @ParameterizedTest
            @ValueSource(ints = {-1, -100})
            @DisplayName("BadRequest 를 응답한다")
            void It_response_BadRequest(int age) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", "test");
                requestMap.put("email", "test1234@naver.com");
                requestMap.put("age", age);
                requestMap.put("hobby", "test");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이메일이 형식에 맞지 않으면")
        class Context_with_invalidEmail {

            @Test
            @DisplayName("BadRequest 를 응답한다")
            void It_response_BadRequest() throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", "test");
                requestMap.put("email", "test1234");
                requestMap.put("age", 10);
                requestMap.put("hobby", "test");

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이메일이 존재하지 않거나 빈값이면")
        class Context_with_nullEmail {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest 를 응답한다")
            void It_response_BadRequest(String src) throws Exception {
                final var requestMap = new HashMap<String, Object>();
                requestMap.put("name", "test");
                requestMap.put("email", src);

                final var content = mapper.writeValueAsString(requestMap);

                final var request = MockMvcRequestBuilders.post(URL)
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON);

                final var result = mockMvc.perform(request);
                result.andExpect(status().isBadRequest());
            }
        }
    }
}
