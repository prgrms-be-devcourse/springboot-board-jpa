package com.kdt.springbootboardjpa.member.controller;

import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequestDto;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static com.kdt.springbootboardjpa.member.domain.Hobby.*;
import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
@Transactional
@SpringBootTest
class MemberControllerIntegrationTest {

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberRepository memberRepository;

    Member member;
    Long memberId;

    @BeforeEach
    void setData() {
        member = Member.builder()
                .name("최은비")
                .age(25)
                .hobby(GAME)
                .build();

        memberId = memberRepository.save(member).getId();
        log.info("BeforeEach memberId : {}", memberId);
    }

    @Test
    @DisplayName("회원(Member) 저장 - 성공")
    void createMember() {
        MemberRequestDto expect = MemberRequestDto.builder()
                .name("save Name")
                .age(10)
                .hobby(MOVIE)
                .build();

        ResponseEntity<MemberResponseDto> actual = memberController.save(expect);

        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody().getId()).isNotNull();
        assertThat(actual.getBody().getName()).isEqualTo(expect.getName());
        assertThat(actual.getBody().getAge()).isEqualTo(expect.getAge());
        assertThat(actual.getBody().getHobby()).isEqualTo(expect.getHobby());
    }

    @Test
    @DisplayName("회원(Member) 수정 - 성공")
    void updateMember() {
        // given
        MemberRequestDto expect = MemberRequestDto.builder()
                .name("변경된 이름")
                .age(100)
                .hobby(EXERCISE)
                .build();

        // when
        ResponseEntity<MemberResponseDto> actual = memberController.update(memberId, expect);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody().getId()).isEqualTo(memberId);
        assertThat(actual.getBody().getName()).isEqualTo(expect.getName());
        assertThat(actual.getBody().getAge()).isEqualTo(expect.getAge());
        assertThat(actual.getBody().getHobby()).isEqualTo(expect.getHobby());
    }

    @Test
    @DisplayName("회원(Member) 삭제 - 성공")
    void deleteMember() {
        // when
        ResponseEntity<Object> actual = memberController.delete(memberId);

        // then
        assertThat(memberRepository.findById(memberId)).isEmpty();
    }
}