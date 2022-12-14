package com.prgrms.board.service;

import com.prgrms.board.domain.Member;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;
import com.prgrms.board.repository.MemberRepository;
import com.prgrms.board.service.converter.MemberConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberConverter converter;

    @Override
    @Transactional
    public Long join(MemberCreateDto createDto) {
        validateDuplicateName(createDto.getName());

        Member newMember = converter.createMemberFromDto(createDto);
        memberRepository.save(newMember);

        return newMember.getId();
    }


    @Override
    public MemberResponseDto findById(Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

        return converter.memberEntityToDto(findMember);
    }


    private void validateDuplicateName(String name) {
        Optional<Member> duplicateMember = memberRepository.findByName(name);
        if (duplicateMember.isPresent()) {
            throw new RuntimeException("이미 사용중인 이름입니다.");
        }
    }
}
