package com.example.jpaboard.web.controller;

import com.example.jpaboard.config.ServiceConfiguration;
import com.example.jpaboard.service.dto.user.UserSaveRequest;
import com.example.jpaboard.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Map;
import java.util.stream.Stream;
import static com.example.jpaboard.web.controller.Common.toJson;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(value = {UserApiController.class, ServiceConfiguration.class})
class UserApiControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Nested
    class save메서드는 {
        @Test
        @DisplayName("유저를 저장하고 유저 아이디를 반환한다")
        void 유저를_저장하고_유저_아이디를_반환한다() throws Exception {
            //given
            Long id = 1L;
            String request = toJson(objectMapper, new UserSaveRequest("이름", 10, "운동"));
            given(userService.save(anyString(), anyInt(), anyString()))
                             .willReturn(id);

            //when, then
            mockMvc.perform(post("/api/v1/users")
                   .contentType(APPLICATION_JSON)
                   .content(request))
                   .andExpect(status().isOk())
                   .andExpect(content().string(String.valueOf(id)));
        }
    }

    @Nested
    class 이름이_null이거나_공백_또는_빈_값이면 {
        @DisplayName("BadRequest 응답을 보낸다")
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" "})
        void BadRequest_응답을_보낸다(String name) throws Exception {
            //given
            String request = toJson(objectMapper, new UserSaveRequest(name, 10, "운동"));

            //when, then
            mockMvc.perform(post("/api/v1/users")
                   .contentType(APPLICATION_JSON)
                   .content(request))
                   .andExpect(status().isBadRequest());
        }
    }

    @Nested
    class 나이가_음수거나_숫자가_아닌_문자열이_주어지면 {
        @DisplayName("BadRequest 응답을 보낸다")
        @ParameterizedTest
        @MethodSource("com.example.jpaboard.web.controller.UserControllerMethodSourceProvider#provideStrings")
        void BadRequest_응답을_보낸다(Object age) throws Exception {
            //given
            Map<String, Object> request = Map.of("name", "이름", "age", age, "hobby", "운동");

            //when, then
            mockMvc.perform(post("/api/v1/users")
                   .contentType(APPLICATION_JSON)
                   .content(request.toString()))
                   .andExpect(status().isBadRequest());
        }
    }
}

class UserControllerMethodSourceProvider {
    public static Stream<Arguments> provideStrings() { // argument source method
        return Stream.of(
                Arguments.of(-10),
                Arguments.of("$"),
                Arguments.of("ajsdf"));
    }
}
