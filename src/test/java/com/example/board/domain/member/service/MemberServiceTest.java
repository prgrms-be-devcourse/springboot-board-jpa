package com.example.board.domain.member.service;

import com.example.board.domain.email.entity.EmailAuth;
import com.example.board.domain.email.repository.EmailAuthRepository;
import com.example.board.domain.member.dto.LoginRequest;
import com.example.board.domain.member.dto.LoginResponse;
import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.dto.PasswordResetRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.role.entity.Role;
import com.example.board.domain.role.entity.RoleType;
import com.example.board.domain.role.repository.RoleRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static com.example.board.factory.member.MemberFactory.createMemberWithRoleUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailAuthRepository emailAuthRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManagerBuilder authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Member member;

    @BeforeEach
    void setUp() {
        member = memberRepository.findById(2L).get();
    }

    @Test
    void 회원가입_테스트() {
        // Given
        MemberCreateRequest request = new MemberCreateRequest(
            "test@naver.com",
            "test123!",
            "tester2",
            25,
            "축구",
            "123456"
        );

        // When
        MemberResponse signUpMember = memberService.createMember(request);

        // Then
        assertThat(signUpMember.email()).isEqualTo(request.email());
    }

    @Test
    void 중복_이메일_회원가입_실패_테스트() {
        // Given
        MemberCreateRequest request = new MemberCreateRequest(
            "user123@naver.com",
            "test123!",
            "tester2",
            25,
            "축구",
            "345678"
        );

        // When & Then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 이메일_인증키_불일치_회원가입_실패_테스트() {
        // Given
        String notExistAuthKey = "000000";
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest(
            "newUser123@naver.com",
            "test123!",
            "tester2",
            25,
            "축구",
            notExistAuthKey
        );

        // When & Then
        assertThatThrownBy(() -> memberService.createMember(memberCreateRequest))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 로그인_테스트() {
        // Given
        LoginRequest loginRequest = new LoginRequest(
            "admin123@naver.com",
            "admin123!"
        );

        // When
        LoginResponse loginMember = memberService.login(loginRequest);

        // Then
        assertThat(loginMember.username()).isEqualTo(loginRequest.email());
    }

    @Test
    void 비밀번호_불일치_로그인_실패_테스트() {
        // Given
        String notExistPassword = "notExistPassword123!";
        LoginRequest loginRequest = new LoginRequest(
            member.getEmail(),
            notExistPassword
        );

        // When
        assertThatThrownBy(() -> memberService.login(loginRequest))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 비밀번호_재설정_테스트() {
        // Given
        PasswordResetRequest request = new PasswordResetRequest(
            member.getEmail(),
            "234567",
            "newPassword123!",
            "newPassword123!"
        );

        // When
        Throwable thrown = catchThrowable(() -> memberService.resetPassword(request));

        // Then
        assertThat(thrown).isNull();
    }
    
    @Test
    void 회원_아이디로_조회_테스트() {
        // Given
        Long memberId = 2L;

        // When
        MemberResponse findMember = memberService.findMemberById(memberId);

        // Then
        assertThat(findMember.id()).isEqualTo(memberId);
    }

    @Test
    void 회원_아이디로_조회_실패_테스트() {
        // Given
        Long notExistId = 5L;

        // When & Then
        assertThatThrownBy(() -> memberService.findMemberById(notExistId))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 회원_수정_테스트() {
        // Given
        Long memberId = member.getId();
        MemberUpdateRequest request = new MemberUpdateRequest(
            "수정된 일반 유저",
            "수정된 취미"
        );

        // When
        MemberResponse updatedMember = memberService.updateMember(memberId, request);

        // Then
        assertThat(updatedMember.name()).isEqualTo(request.name());
        assertThat(updatedMember.hobby()).isEqualTo(request.hobby());
    }
}
