package com.programmers.jpaboard.member.service;

import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}
