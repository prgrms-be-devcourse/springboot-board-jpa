package com.prgms.springbootboardjpa.service;

import com.prgms.springbootboardjpa.dto.CreateMemberRequest;
import com.prgms.springbootboardjpa.dto.DtoMapper;
import com.prgms.springbootboardjpa.dto.MemberDto;
import com.prgms.springbootboardjpa.exception.MemberNotFoundException;
import com.prgms.springbootboardjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long signupMember(CreateMemberRequest createMemberRequest) {
        return memberRepository.save(
                DtoMapper.createMemberRequestToMember(createMemberRequest)
        ).getMemberId();
    }

    @Transactional
    public MemberDto getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .map(DtoMapper::memberToMemberDto)
                .orElseThrow(MemberNotFoundException::new);
    }
}
