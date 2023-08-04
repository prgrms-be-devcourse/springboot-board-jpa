package dev.jpaboard;

import dev.jpaboard.auth.api.AuthController;
import dev.jpaboard.auth.application.AuthService;
import dev.jpaboard.user.application.UserService;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import dev.jpaboard.user.dto.request.UserUpdateRequest;
import dev.jpaboard.user.dto.response.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class UserControllerTest extends ControllerTest {

    @MockBean
    UserService userService;

    @MockBean
    AuthService authService;

    @Test
    void signUp() throws Exception {
        UserCreateRequest request = new UserCreateRequest("qkrdmswl1018@naver.com", "Qwer1234?", "이름", 10, "취이름");
        doNothing().when(authService).signUp(any());

        mockMvc.perform(post("/api/auth/sign-up")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("signup",
                                preprocessRequest(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("비밀번호"),
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER)
                                                .description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING)
                                                .description("취미")
                                )
                        )
                )
//                .andExpect()
                .andExpect(status().isOk());
    }

    @Test
    void login() throws Exception {
        UserLoginRequest request = new UserLoginRequest("qkrdmswl1018@naver.com", "Qwer1234?");
        given(authService.login(any())).willReturn(1L);

        mockMvc.perform(post("/api/auth/login")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("login",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("비밀번호")
                                )
                        )
                ).andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        UserUpdateRequest request = new UserUpdateRequest("이름", "취미");
        doNothing().when(userService).update(any(), any());

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", 1L);

        mockMvc.perform(patch("/api/users/update")
                        .session(session)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("user-update",
                                requestHeaders(
                                        headerWithName("JsessionID").description("세션").getAttributes()
                                ),
                                requestFields(
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("수정할 이름"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING).description("수정할 취미")
                                )
                        )
                ).andExpect(status().isNoContent());
    }

    @Test
    void findUser() throws Exception {
        given(userService.findUser(any())).willReturn(new UserResponse("qwer123@naver.com", "데브코스", 4, "코딩"));

        mockMvc.perform(get("/api/posts/me")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(document("user-find",
                                pathParameters(parameterWithName("id").description("User ID")),
                                responseFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING)
                                                .description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER)
                                                .description("나이"),
                                        fieldWithPath("hobby").type(JsonFieldType.STRING)
                                                .description("취미")
                                )
                        )
                ).andExpect(status().isOk());
    }

}
