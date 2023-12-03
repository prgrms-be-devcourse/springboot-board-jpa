package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberDetailResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    private ObjectMapper objectMapper;

    private MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test@gmail.com", "홍길동", "test1234!",22, "배드민턴");

    @AfterEach
    void tearDown() {
        memberService.deleteAllMembers();
    }

    @Test
    void 회원_생성_호출_테스트() throws Exception {
        // Given
        MemberDetailResponse response = new MemberDetailResponse(
                1L,
                memberCreateRequest.email(),
                memberCreateRequest.name(),
                memberCreateRequest.age(),
                memberCreateRequest.hobby()
        );

        given(memberService.createMember(memberCreateRequest)).willReturn(response);

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
        Long id = 1L;
        MemberDetailResponse response = new MemberDetailResponse(
                id,
                "test@gmail.com",
                "홍길동",
                22,
                "배드민턴"
        );

        given(memberService.findMemberById(id)).willReturn(response);

        // When & Then
        mvc.perform(get("/api/v1/members/{id}", id)
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
        List<MemberDetailResponse> responses = List.of(new MemberDetailResponse(
                1L,
                "test@gmail.com",
                "홍길동",
                22,
                "배드민턴"
        ));

        given(memberService.findAllMembers()).willReturn(responses);

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

    @Test
    void 회원_수정_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        MemberUpdateRequest request = new MemberUpdateRequest("길동이", "수영");
        MemberDetailResponse memberDetailResponse = new MemberDetailResponse(
            id,
            "test@gmail.com",
            request.name(),
            22,
            request.hobby());

        given(memberService.updateMember(id,request)).willReturn(memberDetailResponse);

        // When & Then
        mvc.perform(patch("/api/v1/members/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-update",
                    requestFields(
                        fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
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
    void 회원_아이디로_삭제_호출_테스트() throws Exception {
        // Given
        Long id = 1L;

        // When & Then
        mvc.perform(delete("/api/v1/members/{id}", id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("member-deleteById"));
    }

    @Test
    void 회원_전체_삭제_호출_테스트() throws Exception {
        // Given

        // When & Then
        mvc.perform(delete("/api/v1/members")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("member-deleteAll"));
    }
}
