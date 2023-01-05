package com.kdt.springbootboardjpa.member.controller;

import com.kdt.springbootboardjpa.member.service.MemberService;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequest;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.kdt.springbootboardjpa.member.domain.Hobby.GAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MemberControllerUnitTest {

    @InjectMocks
    MemberController memberController;

    @Mock
    MemberService memberService;

    @Test
    @DisplayName("회원(Member) 저장할 수 있다.")
    void save() {
        // given
        MemberRequest memberRequest = createdMemberRequestDto();
        MemberResponse memberResponse = createdMemberResponseDto();

        given(memberService.save(memberRequest)).willReturn(memberResponse);

        // when
        ResponseEntity<MemberResponse> actual = memberController.save(memberRequest);

        // then
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(actual.getBody().getId()).isEqualTo(memberResponse.getId());
        assertThat(actual.getBody().getName()).isEqualTo(memberResponse.getName());
        assertThat(actual.getBody().getHobby()).isEqualTo(memberResponse.getHobby());
        assertThat(actual.getBody().getAge()).isEqualTo(memberResponse.getAge());
    }

    @Test
    @DisplayName("회원(Member) 수정할 수 있다.")
    void update() {
        // given
        Long memberId = 1L;
        MemberRequest memberRequest = createdMemberRequestDto();
        MemberResponse memberResponse = createdMemberResponseDto();

        given(memberService.update(memberId, memberRequest)).willReturn(memberResponse);

        // when
        ResponseEntity<MemberResponse> actual = memberController.update(memberId, memberRequest);

        // then
        assertThat(actual.getBody().getId()).isEqualTo(memberId);
        assertThat(actual.getBody().getName()).isEqualTo(memberResponse.getName());
        assertThat(actual.getBody().getAge()).isEqualTo(memberResponse.getAge());
        assertThat(actual.getBody().getHobby()).isEqualTo(memberResponse.getHobby());
    }

    @Test
    @DisplayName("회원(Member) 삭제할 수 있다.")
    void delete() {
        // given
        Long memberId = 1L;

        // when
        memberController.delete(memberId);

        // then
//        verify(memberService, times(1)).delete(memberId);
        then(memberService).should().delete(memberId);
    }

    public MemberResponse createdMemberResponseDto() {
        return MemberResponse.builder()
                .id(1L)
                .name("최은비")
                .age(25)
                .hobby(GAME)
                .build();
    }

    public MemberRequest createdMemberRequestDto() {
        return MemberRequest.builder()
                .name("최은비")
                .age(25)
                .hobby(GAME)
                .build();
    }
}
