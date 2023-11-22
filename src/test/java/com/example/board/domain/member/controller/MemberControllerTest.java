package com.example.board.domain.member.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test@gmail.com", "홍길동", 22, "배드민턴");;

    @AfterEach
    void tearDown() {
        memberService.deleteAllMembers();
    }

    @Test
    void 회원_생성_호출_테스트() throws Exception {
        // Given

        // When & Then
        mvc.perform(post("/api/v1/members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberCreateRequest)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("member-create",
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미")
                ),
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미")
                )
            ));
    }

    @Test
    void 회원_아이디_조회_호출_테스트() throws Exception {
        // Given
        MemberResponse savedMember = memberService.createMember(memberCreateRequest);

        // When & Then
        mvc.perform(get("/api/v1/members/{id}", savedMember.id())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-findById",
                responseFields(
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                    fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미")
                )
            ));
    }

    @Test
    void 회원_전체_조회_호출_테스트() throws Exception {
        // Given
        memberService.createMember(memberCreateRequest);

        // When & Then
        mvc.perform(get("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("member-findAll",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("회원 아이디"),
                    fieldWithPath("[].email").type(JsonFieldType.STRING).description("회원 이메일"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("[].age").type(JsonFieldType.NUMBER).description("회원 나이"),
                    fieldWithPath("[].hobby").type(JsonFieldType.STRING).description("회원 취미")
                )
            ));
    }
}
