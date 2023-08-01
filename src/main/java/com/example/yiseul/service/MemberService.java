package com.example.yiseul.service;

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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public List<MemberResponseDto> findMembersByCursor(Long cursor, int size) {
        Pageable pageable = buildCursorPageable(cursor, size);
        List<Member> members = getMemberList(cursor, pageable);

        return members.stream()
                .map(member -> MemberConverter.convertMemberDto(member))
                .collect(Collectors.toList());
    }

    private Pageable buildCursorPageable(Long cursor, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "age").and(Sort.by(Sort.Direction.ASC, "id"));

        return cursor.equals(0L) ? PageRequest.of(0, size, sort) : CursorPageRequest.of(cursor, size, sort);
    }

    private List<Member> getMemberList(Long id, Pageable page) {

        return id.equals(0L)
                ? memberRepository.findByOrderByIdAsc(page)  // 기준이 없는 첫 조회라면 일반 조회
                : memberRepository.findByIdGreaterThanOrderByIdAsc(id, page); // 이후의 조회라면 커서 조회
    }
}
