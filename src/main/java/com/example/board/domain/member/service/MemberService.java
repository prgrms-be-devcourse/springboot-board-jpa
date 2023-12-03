package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.*;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.BusinessException;
import com.example.board.global.security.jwt.JwtAuthentication;
import com.example.board.global.security.jwt.JwtAuthenticationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
    private final AuthenticationManager authenticationManager;


    public MemberDetailResponse createMember(MemberCreateRequest request) {
        validateDuplicateEmail(request.email());
        Member member = memberRepository.save(request.toEntity(passwordEncoder));
        return MemberDetailResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse loginMember(LoginRequest request) {
        JwtAuthenticationToken authToken = new JwtAuthenticationToken(request.principal(), request.credentials());
        JwtAuthenticationToken authenticated = (JwtAuthenticationToken) authenticationManager.authenticate(authToken);
        JwtAuthentication principal = (JwtAuthentication) authenticated.getPrincipal();
        Member member = (Member) authenticated.getDetails();
        return new MemberResponse(principal.token(), principal.username(), member.getId());
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findMemberById(Long id) {
        Member member = getMemberEntity(id);
        return MemberDetailResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
        return MemberDetailResponse.from(member);
    }


    @Transactional(readOnly = true)
    public List<MemberDetailResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberDetailResponse::from)
                .toList();
    }

    public MemberDetailResponse updateMember(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_UPDATE_FAILED));
        member.updateNameAndHobby(request.name(), request.hobby());
        return MemberDetailResponse.from(member);
    }

    public void deleteMemberById(Long id) {
        getMemberEntity(id);
        memberRepository.deleteById(id);
    }

    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new BusinessException(DUPLICATE_EMAIL);
        }
    }

    private Member getMemberEntity(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }
}
