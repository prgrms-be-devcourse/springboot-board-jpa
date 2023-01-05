package com.kdt.springbootboardjpa.member.service;

import com.kdt.springbootboardjpa.global.exception.ExceptionMessage;
import com.kdt.springbootboardjpa.global.exception.NotFoundEntityException;
import com.kdt.springbootboardjpa.member.domain.Member;
import com.kdt.springbootboardjpa.member.service.dto.MemberRequest;
import com.kdt.springbootboardjpa.member.service.dto.MemberResponse;
import com.kdt.springbootboardjpa.member.repository.MemberRepository;
import com.kdt.springbootboardjpa.member.service.converter.MemberConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberConverter memberConverter;
    private final MemberRepository memberRepository;

    public MemberService(MemberConverter memberConverter, MemberRepository memberRepository) {
        this.memberConverter = memberConverter;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public MemberResponse save(MemberRequest memberRequest) {
        Member member = memberRepository.save(memberConverter.requestToMember(memberRequest));
        return memberConverter.memberToResponse(member);
    }

    @Transactional
    public MemberResponse update(Long id, MemberRequest memberRequestDto) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(ExceptionMessage.MEMBER_NOT_EXIST));
        member.changeMember(memberRequestDto.getName(), memberRequestDto.getAge(), memberRequestDto.getHobby());
        return memberConverter.memberToResponse(member);
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityException(ExceptionMessage.MEMBER_NOT_EXIST));
        memberRepository.delete(member);
    }
}
