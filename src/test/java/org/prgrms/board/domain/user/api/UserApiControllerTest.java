package org.prgrms.board.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgrms.board.domain.user.domain.Email;
import org.prgrms.board.domain.user.domain.Name;
import org.prgrms.board.domain.user.domain.Password;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.domain.user.requset.UserLoginRequest;
import org.prgrms.board.domain.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void 유저_생성_요청을_처리할수있다() throws Exception{
        //given
        UserCreateRequest userCreateRequest = UserCreateRequest.builder()
                .firstName("우진")
                .lastName("박")
                .password("kkkk13804943@")
                .email("dbslzld15@naver.com")
                .age(27)
                .build();
        //when
        //then
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("user-create",
                        requestFields(
                                fieldWithPath("firstName").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("lastName").type(JsonFieldType.STRING).description("성"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                        )
                ));
    }

    @Test
    void 로그인_요청을_처리할수있다() throws Exception{
        //given
        UserLoginRequest loginRequest = new UserLoginRequest("dbslzld15@naver.com", "pddmg1394499@");
        when(userService.login(any())).thenReturn(1L);
        //when
        //then
        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-login",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        )
                ));
    }

    @Test
    void 로그아웃_요청을_처리할수있다() throws Exception{
        //given
        //when
        //then
        mockMvc.perform(post("/api/v1/users/logout"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-logout"));
    }
}