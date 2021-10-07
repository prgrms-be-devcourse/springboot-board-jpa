package com.programmers.springbootboard.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberConverter memberConverter;

    @BeforeEach
    void init() {
        // given
        memberRepository.deleteAll();
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("김동건");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when
        memberService.insert(request);

        // then
        long count = memberRepository.count();
        assertThat(1L).isEqualTo(count);
    }

    @Test
    @DisplayName("회원가입")
    void insertMember() throws Exception {
        // given
        Email email = new Email("smjdhappy@naver.com");
        Name name = new Name("성민수");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberSignRequest memberSignRequest = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when // then
        mockMvc.perform(post("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberSignRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-insert",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("회원삭제")
    void deleteMember() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");

        // when // then
        mockMvc.perform(delete("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-delete",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("회원수정")
    void updateMember() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("성민수");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberUpdateRequest memberUpdateRequest = memberConverter.toMemberUpdateRequest(email, name, age, hobby);

        // when // then
        mockMvc.perform(patch("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-update",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.NULL).description("데이터")
                        )
                ));
    }

    @Test
    @DisplayName("회원단건조회")
    void inquiryMember() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");

        // when // then
        mockMvc.perform(get("/api/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(email)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-inquiry",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }

    @Test
    @DisplayName("회원전체조회")
    void inquiryMembers() throws Exception {
        // when // then
        mockMvc.perform(get("/api/members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("데이터"),
                                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data[].age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("data[].hobby").type(JsonFieldType.STRING).description("취미")
                        )
                ));
    }
}