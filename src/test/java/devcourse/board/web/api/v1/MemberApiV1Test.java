package devcourse.board.web.api.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.domain.member.MemberRepository;
import devcourse.board.domain.member.model.Member;
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
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
@Transactional
class MemberApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원 가입")
    void join() throws Exception {
        // given
        MemberJoinRequest joinRequest =
                new MemberJoinRequest("example@gmail.com", "0000", "member");

        // when & then
        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("member-join-v1",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("age").type(NUMBER).description("나이").optional(),
                                fieldWithPath("hobby").type(STRING).description("취미").optional()
                        )));
    }

    @Test
    @DisplayName("로그인 하지 않은 사용자는 자신의 정보를 조회할 수 없다.")
    void should_return_unauthorized_when_non_login_member_access_own_info() throws Exception {
        // given
        Member member = Member.create("email@email.com", "password", "name");
        memberRepository.save(member);

        // when & then
        mockMvc.perform(get("/api/v1/members/my-page", member.getId()))
                .andExpect(status().isUnauthorized())
                .andDo(print())
                .andDo(document("member-my-page-v1",
                        responseFields(
                                fieldWithPath("errorMessage").type(STRING).description("회원 정보 조회 불가 사유")
                        )
                ));
    }
}