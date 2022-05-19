package com.kdt.prgrms.board.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdt.prgrms.board.controller.UserRestController;
import com.kdt.prgrms.board.entity.user.User;
import com.kdt.prgrms.board.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.HashMap;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserRestController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Nested
    @DisplayName("addUser 메서드는")
    class DescribeAddUser {

        String url = "/api/v1/users";

        @Nested
        @DisplayName("이름이 null인 user 생성 요청을 받으면")
        class ContextNullNameAddRequest {

            @Test
            @DisplayName("400 BadRequest응답을 반환한다..")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", null);
                map.put("age", 1);
                map.put("Hobby", 1);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이름이 빈문자열인 user 생성 요청을 받으면")
        class ContextEmptyNameAddRequest {

            @Test
            @DisplayName("400 BadRequest응답을 반환한다..")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", "");
                map.put("age", 1);
                map.put("Hobby", 1);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이름이 공백인 user 생성 요청을 받으면")
        class ContextWhiteSpaceNameAddRequest {

            @Test
            @DisplayName("400 BadRequest응답을 반환한다..")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", "  ");
                map.put("age", 1);
                map.put("Hobby", 1);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("나이가 음수인 user 생성 요청을 받으면")
        class ContextNegativeAgeUserAddRequest {

            @Test
            @DisplayName("400 BadRequest 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", "jaehee");
                map.put("age", -10);
                map.put("hobby", 1);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("이름이 jaehee, 나이가 3, 취미가 없는 생성 요청을 받으면")
        class ContextUserAddRequest {

            @Test
            @DisplayName("201 Created 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", "jaehee");
                map.put("age", 3);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isCreated());

                verify(userService).addUser(any(User.class));
            }
        }

        @Nested
        @DisplayName("이름이 jaehee, 나이가 3, 취미가 음악감상인 생성 요청을 받으면")
        class ContextUserExistHobbyAddRequest {

            @Test
            @DisplayName("201 Created 응답을 반환한다.")
            void itReturnBadRequest() throws Exception {

                final Map<String, Object> map = new HashMap<>();
                map.put("name", "jaehee");
                map.put("age", 3);
                map.put("hobby", 0);

                MockHttpServletRequestBuilder request = post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(map));

                mockMvc.perform(request)
                        .andExpect(status().isCreated());

                verify(userService).addUser(any(User.class));
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
