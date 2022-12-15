package com.prgrms.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
class MemberApiControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberService memberService;
    private Long savedMemberId;

    @BeforeEach
    void setup() {
        MemberCreateDto createDto = MemberCreateDto.builder()
                .name("member1")
                .age(26)
                .hobby("youTube")
                .build();

        savedMemberId = memberService.join(createDto);
    }


    @Test
    void 멤버_조회() throws Exception {
        mockMvc.perform(get("/api/v1/members/{id}", savedMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savedMemberId)))
                .andExpect(status().isOk()) // 200
                .andDo(print());
    }
}