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

@SpringBootTest // 답 : 통합과 단위의 문제 전체와 흐름에 파악할 수 있음, 단위테스트 : 의존하는 객체를 mock으로 만들어서 객체만을 테스트하겠다는 의도, 결과를 이미 선언하고 테스트를 하면서 외부 변수에 대응할 수 없음, 운영환경과 다른 점이 많을 수 있음
        // 선택의 영역 : 서비스는 mock (뭔가 짜여진 테스트를 돌린다는 많은 생각들) every를 사용하더라도 동작을 검증할 수 있어 의미가 있다.  db/데이터 검증은 통합 테스트를 사용하기도 한다.

class MemberServiceSpringBootTest {

    @Autowired
    MemberService memberService;

    MemberCreateRequestDto createRequestDto;
    MemberResponseDto savedMemberDto;

    @BeforeEach // 답 : 통합테스트는 서비스의 전체적인 로직을  검증하는 문제, 흐름상 어색하지 않은 문제! save가 존재하는지, delete후 존재 확인 등 유의미한 검증이 이루어질 수 있다.
    void setUp(){
        createRequestDto = new MemberCreateRequestDto("hihi", 22, "basketball");
        savedMemberDto = memberService.createMember(createRequestDto);
    }

    @Test
    @DisplayName("멤버 저장에 성공한다.") // 핀
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
        // 핀 :  createdat은 어떻게 확인을 해주나? 어디서?
    }

//    @Test //핀
//    @DisplayName("멤버 삭제에 성공한다.")
//    void deleteMember(){ //예외가 던져지지 않으면 성공, 에외가 발생하는 검증
//        // when
//        memberService.deleteMember(savedMemberDto.id());
//
//        //then
//        assertThat().isNull();
//    }
}