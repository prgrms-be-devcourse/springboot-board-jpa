package com.example.yiseul.service;

import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
// data jpa test 를 사용해서 서비스-레포 테스트 시도해보기 : 수정
class MemberServiceSpringBootTest {

    @Autowired
    MemberService memberService;

    MemberCreateRequestDto createRequestDto;
    MemberResponseDto savedMemberDto;

    @BeforeEach
    void setUp() {
        createRequestDto = new MemberCreateRequestDto("hihi", 22, "basketball");
        savedMemberDto = memberService.createMember(createRequestDto);
    }

    @Test
    @DisplayName("멤버 저장에 성공한다.")
    void createMember() {
        // then
        assertThat(savedMemberDto).isNotNull();
    }

    @Test
    @DisplayName("멤버 조회에 성공한다.")
    void getMember() {
        // when
        MemberResponseDto memberResponseDto = memberService.getMember(savedMemberDto.memberId());

        // then
        assertThat(memberResponseDto.name()).isEqualTo("hihi");
        assertThat(memberResponseDto.age()).isEqualTo(22);
        assertThat(memberResponseDto.hobby()).isEqualTo("basketball");
    }
}

