package com.example.board.domain.member.service;

import com.example.board.domain.email.entity.EmailAuth;
import com.example.board.domain.email.repository.EmailAuthRepository;
import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.role.entity.Role;
import com.example.board.domain.role.entity.RoleType;
import com.example.board.domain.role.repository.RoleRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private EmailAuthRepository emailAuthRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private Member member = createMemberWithRoleUser();

    @Test
    void 멤버_생성_테스트() {
        // Given
        MemberCreateRequest request = new MemberCreateRequest(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getHobby(),
                "testKey"
        );
        EmailAuth emailAuth = new EmailAuth(request.authKey(), request.email(), "SIGN-UP");

        given(roleRepository.findByRoleType(RoleType.USER)).willReturn(Optional.of(new Role(RoleType.USER)));
        given(emailAuthRepository.findByEmailAndPurpose(request.email(), "SIGN-UP")).willReturn(Optional.of(emailAuth));


        // When
        MemberResponse savedMember = memberService.createMember(request);

        // Then
        assertThat(savedMember.email()).isEqualTo(request.email());
        assertThat(savedMember.name()).isEqualTo(request.name());
        assertThat(savedMember.age()).isEqualTo(request.age());
        assertThat(savedMember.hobby()).isEqualTo(request.hobby());
    }

    @Test
    void 이메일_중복_테스트() {
        // Given
        MemberCreateRequest request = new MemberCreateRequest(
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getAge(),
                member.getHobby(),
                ""
        );
        given(memberRepository.existsMemberByEmail(member.getEmail())).willReturn(true);

        // When & Then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(CustomException.class);
    }
    
    @Test
    void 회원_아이디_조회_테스트() {
        // Given
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        // When
        MemberResponse findMember = memberService.findMemberById(member.getId());

        // Then
        assertThat(findMember.email()).isEqualTo(member.getEmail());
        assertThat(findMember.name()).isEqualTo(member.getName());
        assertThat(findMember.age()).isEqualTo(member.getAge());
        assertThat(findMember.hobby()).isEqualTo(member.getHobby());
    }

    @Test
    void 회원_아이디_조회_실패_테스트() {
        // Given
        Long notExistId = 2L;
        given(memberRepository.findById(notExistId)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> memberService.findMemberById(notExistId))
            .isInstanceOf(CustomException.class);
    }

    @Test
    void 회원_전체조회_테스트() {
        // Given
        List<Member> members = List.of(member);
        given(memberRepository.findAll()).willReturn(members);

        // When
        List<MemberResponse> findMembers = memberService.findAllMembers();
        
        // Then
        assertThat(findMembers).hasSize(1);
        assertThat(findMembers.get(0).id()).isEqualTo(member.getId());
    }
    
    @Test
    void 회원_수정_테스트() {
        // Given
        MemberUpdateRequest request = new MemberUpdateRequest("길동이", "수영");
        given(memberRepository.findById(member.getId())).willReturn(Optional.of(member));

        // When
        MemberResponse updatedMember = memberService.updateMember(member.getId(), request);

        // Then
        assertThat(updatedMember.name()).isEqualTo(request.name());
        assertThat(updatedMember.hobby()).isEqualTo(request.hobby());
    }
}
