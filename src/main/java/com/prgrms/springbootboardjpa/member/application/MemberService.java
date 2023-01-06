package com.prgrms.springbootboardjpa.member.application;

import com.prgrms.springbootboardjpa.member.exception.MemberNotFoundByIdException;
import com.prgrms.springbootboardjpa.member.exception.MemberNotFoundLoginException;
import com.prgrms.springbootboardjpa.member.domain.Member;
import com.prgrms.springbootboardjpa.member.domain.MemberRepository;
import com.prgrms.springbootboardjpa.member.dto.JoinMemberRequest;
import com.prgrms.springbootboardjpa.member.dto.LoginRequest;
import com.prgrms.springbootboardjpa.member.dto.MemberResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberResponse saveMember(JoinMemberRequest joinMemberRequest) {
        Member saveMember = memberRepository.save(joinMemberRequest.toMember());
        return new MemberResponse(saveMember);
    }

    public Member login(LoginRequest loginRequest) {
        Optional<Member> loginMember = memberRepository.findMemberForLogin(loginRequest.getEmail(), loginRequest.getPassword());
        return loginMember.orElseThrow(() -> new MemberNotFoundLoginException());
    }

    public MemberResponse findById(Long memberId) {
        Member foundMember = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundByIdException(memberId));
        return new MemberResponse(foundMember);
    }

}
