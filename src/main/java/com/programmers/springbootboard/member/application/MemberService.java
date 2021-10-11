package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.DuplicationArgumentException;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.*;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
    //TODO :: 파사드 레이어 진행해보기
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    public MemberService(MemberRepository memberRepository, MemberConverter memberConverter) {
        this.memberRepository = memberRepository;
        this.memberConverter = memberConverter;
    }

    @Transactional
    public MemberDetailResponse insert(MemberSignRequest request) {
        Email email = new Email(request.getEmail());
        if (existsByEmail(email)) {
            throw new DuplicationArgumentException(ErrorMessage.DUPLICATION_MEMBER_EMAIL);
        }

        Member member = memberConverter.toMember(request);
        memberRepository.save(member);
        member.addByInformation(member.getId());

        return memberConverter.toMemberDetailResponse(member);
    }

    private boolean existsByEmail(Email email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public MemberDeleteResponse deleteById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
        });
        memberRepository.deleteById(id);
        return memberConverter.toMemberDeleteResponse(member.getId(), member.getEmail());
    }

    //역할에 맞는 함수형을 쓰자!
    @Transactional
    public MemberDetailResponse update(Long id, MemberUpdateRequest request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        member.update(request);
        return memberConverter.toMemberDetailResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findById(Long id) {
        return memberRepository.findById(id)
                .map(memberConverter::toMemberDetailResponse)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
    }

    @Transactional(readOnly = true)
    public Page<MemberDetailResponse> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable)
                .map(memberConverter::toMemberDetailResponse);
    }

    @Transactional(readOnly = true)
    public Member findByEmail(Email email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
    }

    // TODO 필요없는 코드가 test를 위해서??? 수정하기!
    @Transactional
    public void deleteAll() {
        memberRepository.deleteAll();
    }

    // 이건 속임수!!! TEST-CODE, 테스터블하게 구성하자!!!
    @Transactional(readOnly = true)
    public long count() {
        return memberRepository.count();
    }
}
