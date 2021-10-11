package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.exception.ErrorMessage;
import com.programmers.springbootboard.exception.error.DuplicationArgumentException;
import com.programmers.springbootboard.exception.error.NotFoundException;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.MemberDeleteResponse;
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

    // 파사드 레이어 진행해보기
    // 인서트 내부에서 체크해주자!
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
        member.lastModifiedId(member.getId());
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
    public List<MemberDetailResponse> findAll() {
        return memberRepository.findAll()
                .stream()
                .map(memberConverter::toMemberDetailResponse)
                .collect(Collectors.toList());
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
