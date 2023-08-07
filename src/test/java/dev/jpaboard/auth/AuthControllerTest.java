package dev.jpaboard.auth;

import com.epages.restdocs.apispec.Schema;
import dev.jpaboard.ControllerTest;
import dev.jpaboard.auth.api.AuthController;
import dev.jpaboard.auth.application.AuthService;
import dev.jpaboard.user.dto.request.UserCreateRequest;
import dev.jpaboard.user.dto.request.UserLoginRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.resourceDetails;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
public class AuthControllerTest extends ControllerTest {

    @MockBean
    private AuthService authService;

    @DisplayName("자신의 정보를 입력하여 회원가입할 수 있다.")
    @Test
    void signUp() throws Exception {
        UserCreateRequest request = new UserCreateRequest("qkrdmswl1018@naver.com", "Qwer1234?", "이름", 10, "취이름");
        doNothing().when(authService).signUp(request);

        mockMvc.perform(post("/api/auth/sign-up")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andDo(document("signup",
                                resourceDetails().tag("Auth").description("회원가입")
                                        .requestSchema(Schema.schema("SignUpRequest")),
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
                .andExpect(status().isOk());
    }

    @DisplayName("회원가입된 유저는 이메일과 비밀번호로 로그인할 수 있다.")
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
                                resourceDetails().tag("Auth").description("로그인")
                                        .requestSchema(Schema.schema("LoginRequest")),
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                requestFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING)
                                                .description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING)
                                                .description("비밀번호")
                                )
                        )
                )
                .andExpect(content().string("1"))
                .andExpect(status().isOk());
    }

}
