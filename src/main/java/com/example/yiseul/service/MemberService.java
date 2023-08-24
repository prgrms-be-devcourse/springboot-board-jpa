package com.example.yiseul.service;

import com.example.yiseul.converter.MemberConverter;
import com.example.yiseul.domain.Member;
import com.example.yiseul.dto.member.*;
import com.example.yiseul.global.exception.BaseException;
import com.example.yiseul.global.exception.ErrorCode;
import com.example.yiseul.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j

@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto createRequestDto) {
        Member member = MemberConverter.convertMember(createRequestDto);
        Member savedMember = memberRepository.save(member);

        return MemberConverter.convertMemberResponseDto(savedMember);
    }


    public MemberPageResponseDto getMembers(Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);

        return MemberConverter.convertMemberPageResponseDto(page);
    }

    public MemberResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("MemberService : Member {} is not found",memberId);

                    return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
                });

        return MemberConverter.convertMemberResponseDto(member);
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto updateRequestDto) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> {
                    log.error("MemberService : Member {} is not found",memberId);

                    return new BaseException(ErrorCode.MEMBER_NOT_FOUND);
                });

        member.updateInfo(updateRequestDto.name(), updateRequestDto.age(), updateRequestDto.hobby());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            log.error("MemberService : Member {} is not found",memberId);

            throw new BaseException(ErrorCode.MEMBER_NOT_FOUND);
        }

        memberRepository.deleteById(memberId);
    }

    public MemberCursorResponseDto findMemberByCursor(Long cursorId) {
        List<Member> members = memberRepository.findTop2ByIdGreaterThanOrderByIdAsc(cursorId);

        return MemberConverter.convertMemberCursorResponseDto(members);
    }
}
