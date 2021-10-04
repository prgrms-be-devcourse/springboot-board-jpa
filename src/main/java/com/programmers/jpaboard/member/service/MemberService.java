package com.programmers.jpaboard.member.service;

import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
