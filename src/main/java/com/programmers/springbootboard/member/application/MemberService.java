package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    public MemberService(MemberRepository memberRepository, MemberConverter memberConverter) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(Email email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void insert(MemberSignRequest request) {
        Member member = memberConverter.toMember(request);
        memberRepository.save(member);
        member.addByInformation(member.getId());
    }

    @Transactional
    public void deleteByEmail(Email email) {
        memberRepository.deleteByEmail(email);
    }

    @Transactional
    public MemberDetailResponse update(MemberUpdateRequest request) {
        Email email = new Email(request.getEmail());
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        member.update(request);
        member.lastModifiedId(member.getId());
        return memberConverter.toMemberDetailResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findByEmail(Email email) {
        return memberRepository.findByEmail(email)
                .map(memberConverter::toMemberDetailResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
    }

    @Transactional(readOnly = true)
    public List<MemberDetailResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(memberConverter::toMemberDetailResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }
}
