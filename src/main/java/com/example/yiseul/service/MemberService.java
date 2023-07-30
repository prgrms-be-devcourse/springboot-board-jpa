package com.example.yiseul.service;

import com.example.yiseul.controller.CursorResult;
import com.example.yiseul.converter.MemberConverter;
import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.MemberCreateRequestDto;
import com.example.yiseul.dto.member.MemberResponseDto;
import com.example.yiseul.dto.member.MemberUpdateRequestDto;
import com.example.yiseul.global.exception.ErrorCode;
import com.example.yiseul.global.exception.MemberException;
import com.example.yiseul.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true) // 질문
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto createRequestDto) {
        Member member = MemberConverter.convertMember(createRequestDto);
        Member savedMember = memberRepository.save(member);

        return MemberConverter.convertMemberDto(savedMember);
    }

    public Page<MemberResponseDto> getMembers(Pageable pageable) {

        return memberRepository.findAll(pageable)
                .map(member -> MemberConverter.convertMemberDto(member));
    }

    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.convertMemberDto(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto updateRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(ErrorCode.MEMBER_NOT_FOUND));

        member.updateInfo(updateRequestDto.name(), updateRequestDto.age(), updateRequestDto.hobby());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberException(ErrorCode.MEMBER_NOT_FOUND);
        }

        memberRepository.deleteById(memberId);
    }

}
