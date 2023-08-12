package com.example.jpaboard.member.service;

import com.example.jpaboard.global.exception.EntityNotFoundException;
import com.example.jpaboard.member.domain.Member;
import com.example.jpaboard.member.service.dto.MemberFindResponse;
import com.example.jpaboard.member.service.mapper.MemberMapper;

import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public MemberFindResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("존재하지 않은 고객입니다."));

        return new MemberFindResponse(member);
    }

}
