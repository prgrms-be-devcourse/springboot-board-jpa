package com.prgrms.springbootboardjpa.member.application;

import com.prgrms.springbootboardjpa.member.domain.Member;
import com.prgrms.springbootboardjpa.member.dto.JoinMemberRequest;
import com.prgrms.springbootboardjpa.member.dto.LoginRequest;
import com.prgrms.springbootboardjpa.member.dto.MemberResponse;
import com.prgrms.springbootboardjpa.member.exception.MemberNotFoundLoginException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;


    @DisplayName("member를 저장할 수 있다.")
    @Test
    void saveTest() {
        JoinMemberRequest joinMemberRequest = new JoinMemberRequest("email1", "password1", "name1", 10, "hobby");
        MemberResponse memberResponse = memberService.saveMember(joinMemberRequest);

        MemberResponse foundMemberResponse = memberService.findById(memberResponse.getMemberId());

        Assertions.assertThat(memberResponse).usingRecursiveComparison().isEqualTo(foundMemberResponse);
    }

    @DisplayName("login을 위해 email과 password가 일치하는 Member를 찾을 수 있다.")
    @Test
    void loginSuccessTest() {
        JoinMemberRequest joinMemberRequest = new JoinMemberRequest("email1", "password1", "name1", 10, "hobby");
        MemberResponse memberResponse = memberService.saveMember(joinMemberRequest);

        LoginRequest loginRequest = new LoginRequest(memberResponse.getEmail(), memberResponse.getPassword());
        Member loginMember = memberService.login(loginRequest);

        Assertions.assertThat(new MemberResponse(loginMember)).usingRecursiveComparison().isEqualTo(memberResponse);
    }

    @DisplayName("login 실패시 MemberNotFoundLoginException가 발생한다.")
    @Test
    void loginFailTest() {
        JoinMemberRequest joinMemberRequest = new JoinMemberRequest("email1", "password1", "name1", 10, "hobby");
        MemberResponse memberResponse = memberService.saveMember(joinMemberRequest);

        LoginRequest loginRequest = new LoginRequest(memberResponse.getEmail(), "1234");

        Assertions.assertThatThrownBy(() -> memberService.login(loginRequest))
                .isInstanceOf(MemberNotFoundLoginException.class);
    }

}