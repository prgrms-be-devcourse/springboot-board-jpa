package com.kdt.board.user.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.board.user.application.dto.request.UserRegistrationRequestDto;
import com.kdt.board.user.presentation.UserController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("User 등록 요청이 들어올 때")
    class Describe_register_user {

        final String url = "/api/users";

        @Nested
        @DisplayName("이름이 존재하지 않거나 빈 값이면")
        class Context_with_null_or_empty_name {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest를 응답한다.")
            void It_response_BadRequest(String name) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", name);
                requestMap.put("email", "test@test.com");

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이메일이 존재하지 않거나 빈 값이면")
        class Context_with_null_or_empty_email {

            @ParameterizedTest
            @NullAndEmptySource
            @DisplayName("BadRequest를 응답한다.")
            void It_response_BadRequest(String email) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", email);

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이메일이 유효하지 않은 형식이면")
        class Context_with_invalid_email {

            @ParameterizedTest
            @ValueSource(strings = {"test", "test@", "test.com"})
            @DisplayName("BadRequest를 응답한다.")
            void It_response_BadRequest(String email) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", email);

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("나이가 음수면")
        class Context_with_zero_age {

            @ParameterizedTest
            @ValueSource(ints = {-1, -1000})
            @DisplayName("BadRequest를 응답한다.")
            void It_response_BadRequest(int age) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", age);

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("나이가 문자로 이루어지면")
        class Context_with_string_age {

            @ParameterizedTest
            @ValueSource(strings = {"test", "10 1"})
            @DisplayName("BadRequest를 응답한다.")
            void It_response_BadRequest(String age) throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", age);

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효한 이름(필수값)과 이메일(필수값)이면")
        class Context_with_valid_name_and_email {

            @Test
            @DisplayName("Created를 응답한다.")
            void It_response_Created() throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", "test@test.com");

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                verify(userService, times(1)).register(any(UserRegistrationRequestDto.class));
                resultActions.andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("유효한 이름(필수값), 이메일(필수값), 나이, 취미이면")
        class Context_with_valid_name_email_age_and_hobby {

            @Test
            @DisplayName("Created를 응답한다.")
            void It_response_Created() throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", "test@test.com");
                requestMap.put("age", "10");
                requestMap.put("hobby", "test");

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                verify(userService, times(1)).register(any(UserRegistrationRequestDto.class));
                resultActions.andExpect(status().isCreated());
            }
        }

        @Nested
        @DisplayName("IllegalArgumentException 넘어온다면")
        class Context_with_passed_over_IllegalArgumentException {

            @Test
            @DisplayName("InternalServerError를 반환한다")
            void It_response_InternalServerError() throws Exception {
                //given
                final HashMap<String, Object> requestMap = new HashMap<>();
                requestMap.put("name", "test");
                requestMap.put("email", "test@test.com");
                requestMap.put("age", "10");
                requestMap.put("hobby", "test");

                doThrow(new IllegalArgumentException()).when(userService).register(any(UserRegistrationRequestDto.class));

                //when
                final ResultActions resultActions = registerUserPerform(url, requestMap);

                //then
                resultActions.andExpect(status().isInternalServerError());
            }
        }
    }

    private ResultActions registerUserPerform(final String url, final HashMap<String, Object> requestMap) throws Exception {
        return mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestMap)));
    }
}
