package com.example.board.domain.member.controller;

import com.example.board.domain.email.service.MailService;
import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.service.MemberService;
import com.example.board.global.security.config.SecurityConfig;
import com.example.board.global.security.jwt.filter.JwtFilter;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.board.factory.member.MemberFactory.createMemberWithRoleUser;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(value = MemberController.class,
    excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtFilter.class),
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtTokenProvider.class)
    }
)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MailService mailService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Member member = createMemberWithRoleUser();

    private MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
            member.getEmail(),
            member.getPassword(),
            member.getName(),
            member.getAge(),
            member.getHobby(),
            "testKey"
    );

    @Test
    void 회원_생성_호출_테스트() throws Exception {
        // Given
        MemberResponse response = new MemberResponse(
                1L,
                memberCreateRequest.email(),
                memberCreateRequest.name(),
                memberCreateRequest.age(),
                memberCreateRequest.hobby()
        );
        given(memberService.createMember(memberCreateRequest)).willReturn(response);

        // When & Then
        mvc.perform(post("/api/v1/members/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("member-create",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("회원 이름"),
                                fieldWithPath("age").type(JsonFieldType.NUMBER).description("회원 나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("회원 취미"),
                                fieldWithPath("authKey").type(JsonFieldType.STRING).description("이메일 인증키")
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
        MemberResponse response = new MemberResponse(
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
        List<MemberResponse> responses = List.of(new MemberResponse(
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
    @WithMockUser
    void 회원_수정_호출_테스트() throws Exception {
        // Given
        Long id = 1L;
        MemberUpdateRequest request = new MemberUpdateRequest("길동이", "수영");
        MemberResponse memberResponse = new MemberResponse(
            id,
            "test@gmail.com",
            request.name(),
            22,
            request.hobby());
        given(memberService.updateMember(id,request)).willReturn(memberResponse);

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
    @WithMockUser
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
    @WithMockUser
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
