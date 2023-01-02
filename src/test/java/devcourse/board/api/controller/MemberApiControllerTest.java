package devcourse.board.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import devcourse.board.api.authentication.CookieConst;
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

import javax.servlet.http.Cookie;

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
class MemberApiControllerTest {

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
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("member-join",
                        requestFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("password").type(STRING).description("비밀번호"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("age").type(NUMBER).description("나이").optional(),
                                fieldWithPath("hobby").type(STRING).description("취미").optional()
                        )));
    }

    @Test
    @DisplayName("회원 단건 조회")
    void getMember() throws Exception {
        // given
        Member member = Member.create(
                "example@email.com",
                "0000",
                "member",
                null,
                null);
        memberRepository.save(member);

        Cookie idCookie = new Cookie(CookieConst.MEMBER_ID, String.valueOf(member.getId()));

        // when & t경hen
        mockMvc.perform(get("/members")
                        .cookie(idCookie))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-get-one",
                        responseFields(
                                fieldWithPath("email").type(STRING).description("이메일"),
                                fieldWithPath("name").type(STRING).description("이름"),
                                fieldWithPath("age").type(NUMBER).description("나이").optional(),
                                fieldWithPath("hobby").type(STRING).description("취미").optional()
                        )
                ));
    }
}