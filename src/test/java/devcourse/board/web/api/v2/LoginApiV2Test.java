package devcourse.board.web.api.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class LoginApiV2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("로그인 성공")
    void login() throws Exception {
        // given
        String email = "email@email.com";
        String password = "password";
        Member member = Member.create(email, password, "name");
        memberRepository.save(member);

        LoginRequest loginRequest = new LoginRequest(email, password);

        // when & then
        mockMvc.perform(post("/api/v2/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-login-v2",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        )));
    }

    @Test
    @DisplayName("로그인 실패 - 유효하지 않은 이메일을 입력시 400을 리턴한다.")
    void should_return_400_for_invalid_email_when_login() throws Exception {
        // given
        String email = "email@email.com";
        String password = "password";

        Member member = Member.create(email, password, "name");
        memberRepository.save(member);

        String invalidEmail = "invalidEmail@email.com";
        LoginRequest loginRequest = new LoginRequest(invalidEmail, password);

        // when & then
        mockMvc.perform(post("/api/v2/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("member-login-fail-invalid-email-v2",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("errorMessage").type(STRING).description("로그인 실패 사유")
                        )));
    }

    @Test
    @DisplayName("로그인 실패 - 유효하지 않은 비밀번호를 입력시 400을 리턴한다.")
    void should_return_400_for_invalid_password_when_login() throws Exception {
        // given
        String email = "email@email.com";
        String password = "password";

        Member member = Member.create(email, password, "name");
        memberRepository.save(member);

        String invalidPassword = "invalidPassword";
        LoginRequest loginRequest = new LoginRequest(email, invalidPassword);

        // when & then
        mockMvc.perform(post("/api/v2/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andDo(document("member-login-fail-invalid-password-v2",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("errorMessage").type(STRING).description("로그인 실패 사유")
                        )));
    }

    @Test
    @DisplayName("로그아웃 성공")
    void logout_success() throws Exception {
        // when & then
        mockMvc.perform(post("/api/v2/logout"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-logout-v2"));
    }
}