package com.prgrms.board.service;

import com.prgrms.board.converter.EntityConverter;
import com.prgrms.board.domain.Member;
import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.dto.response.MemberResponseDto;
import com.prgrms.board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EntityConverter converter;

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
                .orElseThrow(() -> new IllegalArgumentException("exception.member.id.null"));

        return converter.memberEntityToDto(findMember);
    }


    @Override
    public List<MemberResponseDto> findAll() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(member -> converter.memberEntityToDto(member))
                .collect(Collectors.toList());
    }


    private void validateDuplicateName(String name) {
        Optional<Member> duplicateMember = memberRepository.findByName(name);
        if (duplicateMember.isPresent()) {
            throw new IllegalArgumentException("exception.member.name.duplicate");
        }
    }
}
