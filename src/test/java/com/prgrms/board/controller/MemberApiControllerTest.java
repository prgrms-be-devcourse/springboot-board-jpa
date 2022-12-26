package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@Slf4j
@Transactional
class MemberApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    private Long savedMemberId;
    private MemberCreateDto createDto;

    @BeforeEach
    void setup() {
        createDto = MemberCreateDto.builder()
                .name("member1")
                .age(26)
                .hobby("youTube")
                .build();

        savedMemberId = memberService.join(createDto);
    }

    @Test
    void 멤버_조회() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/members/{id}", savedMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedMemberId)))
                .andExpect(jsonPath("$.data.id").value(savedMemberId))
                .andExpect(jsonPath("$.data.name").value(createDto.getName()))
                .andExpect(jsonPath("$.data.age").value(createDto.getAge()))
                .andExpect(jsonPath("$.data.hobby").value(createDto.getHobby()))
                .andDo(print())

                .andDo(document("member-find",
                        pathParameters(
                                parameterWithName("id").description("사용자 아이디")
                        ),
                        responseFields(
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회원PK"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("회원이름"),
                                fieldWithPath("data.age").type(JsonFieldType.NUMBER).description("나이"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("data.posts[]").type(JsonFieldType.ARRAY).description("작성한 글 목록"),
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )
                ));
    }
}