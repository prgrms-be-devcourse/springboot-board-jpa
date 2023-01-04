package devcourse.board.web.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.domain.login.model.LoginRequest;
import devcourse.board.domain.member.MemberService;
import devcourse.board.domain.member.model.MemberJoinRequest;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class LoginApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("로그인")
    void login() throws Exception {
        // given
        String email = "example@gmail.com";
        String password = "0000";
        String name = "member";

        MemberJoinRequest joinRequest = new MemberJoinRequest(email, password, name);
        memberService.join(joinRequest);

        LoginRequest loginRequest = new LoginRequest(email, password);

        // when & then
        mockMvc.perform(post("/api/v1/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-login-v1",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호")
                        )));
    }

    @Test
    @DisplayName("로그아웃")
    void logout() throws Exception {
        // when & then
        mockMvc.perform(post("/api/v1/logout"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-logout-v1"));
    }
}