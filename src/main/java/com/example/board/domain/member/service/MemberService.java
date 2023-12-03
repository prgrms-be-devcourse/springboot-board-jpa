package com.example.board.domain.member.service;

import com.example.board.domain.email.entity.EmailAuth;
import com.example.board.domain.email.repository.EmailAuthRepository;
import com.example.board.domain.member.dto.*;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.role.entity.Role;
import com.example.board.domain.role.entity.RoleType;
import com.example.board.domain.role.repository.RoleRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.board.global.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleRepository roleRepository;
    private final EmailAuthRepository emailAuthRepository;

    public MemberResponse createMember(MemberCreateRequest request) {
        validateDuplicateEmail(request.email());
        validateEmailAuthKey(request);
        List<Role> roles = List.of(roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new CustomException(ROLE_NOT_FOUND)));

        Member member = new Member(
                request.email(),
                passwordEncoder.encode(request.password()),
                request.name(),
                request.age(),
                request.hobby(),
                roles
        );

        memberRepository.save(member);
        emailAuthRepository.deleteByEmail(member.getEmail());
        return MemberResponse.from(member);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));

        member.checkPassword(passwordEncoder, loginRequest.password());
        String accessToken = getToken(loginRequest);
        return LoginResponse.from(loginRequest.email(), accessToken);
    }

    @Transactional
    public void resetPassword(PasswordResetRequest request) {
        Member member = memberRepository.findByEmail(request.email())
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
        validateEmailAuthKey(request);
        validatePasswordEqualsVerifyPassword(request);

        member.updatePassword(passwordEncoder.encode(request.password()));
        emailAuthRepository.deleteByEmail(request.email());
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberById(Long id) {
        Member member = getMemberEntity(id);
        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponse::from)
                .toList();
    }

    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MEMBER_UPDATE_FAILED));
        member.updateNameAndHobby(request.name(), request.hobby());
        return MemberResponse.from(member);
    }

    public void deleteMemberById(Long id) {
        Member member = getMemberEntity(id);
        member.delete();
    }

    public void deleteMemberByIds(List<Long> memberIds) {
        List<Member> members = memberRepository.findMembersByIds(memberIds);
        members.forEach(Member::delete);
    }

    public void deleteAllMembers() {
        List<Member> members = memberRepository.findAll();
        members.forEach(Member::delete);
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new CustomException(DUPLICATE_EMAIL);
        }
    }

    private Member getMemberEntity(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
    }

    private String getToken(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                );
        Authentication authenticate = authenticationManager.getObject().authenticate(authenticationToken);
        return jwtTokenProvider.createToken(authenticate);
    }

    private void validateEmailAuthKey(MemberCreateRequest request) {
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndPurpose(request.email(), "SIGN-UP")
                .orElseThrow(() -> new CustomException(ErrorCode.VALIDATE_EMAIL_FAILED));
        if (!emailAuth.getKey().equals(request.authKey())) {
            throw new CustomException(ErrorCode.VALIDATE_EMAIL_FAILED);
        }
    }

    private void validateEmailAuthKey(PasswordResetRequest request) {
        EmailAuth emailAuth = emailAuthRepository.findByEmailAndPurpose(request.email(), "RESET-PASSWORD")
                .orElseThrow(() -> new CustomException(ErrorCode.VALIDATE_EMAIL_FAILED));
        if (!emailAuth.getKey().equals(request.authKey())) {
            throw new CustomException(ErrorCode.VALIDATE_EMAIL_FAILED);
        }
    }

    private void validatePasswordEqualsVerifyPassword(PasswordResetRequest request) {
        if (!request.password().equals(request.verifyPassword())) {
            throw new CustomException(ErrorCode.NOT_EQUALS_RESET_PASSWORD);
        }
    }
}
