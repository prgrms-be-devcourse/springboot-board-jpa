package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.error.ErrorMessage;
import com.programmers.springbootboard.error.exception.DuplicationArgumentException;
import com.programmers.springbootboard.error.exception.NotFoundException;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.dto.response.MemberSignResponse;
import com.programmers.springbootboard.member.dto.response.MemberUpdateResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberDeleteBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberFindBundle;
import com.programmers.springbootboard.member.dto.bundle.MemberSignBundle;
import com.programmers.springbootboard.member.dto.response.MemberDeleteResponse;
import com.programmers.springbootboard.member.dto.response.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.bundle.MemberUpdateBundle;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    //TODO :: 파사드 레이어 진행해보기
    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;

    @Transactional
    public MemberSignResponse insert(MemberSignBundle bundle) {
        if (existsByEmail(bundle.getEmail())) {
            throw new DuplicationArgumentException(ErrorMessage.DUPLICATION_MEMBER_EMAIL);
        }

        Member member = memberConverter.toMember(bundle);
        member.addByInformation(member.getId());

        Member memberEntity = memberRepository.save(member);
        return memberConverter.toMemberSignResponse(memberEntity);
    }

    private boolean existsByEmail(Email email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public MemberDeleteResponse delete(MemberDeleteBundle bundle) {
        Member member = memberRepository.findById(bundle.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        memberRepository.deleteById(bundle.getId());
        return memberConverter.toMemberDeleteResponse(member.getId(), member.getEmail());
    }

    @Transactional
    public MemberUpdateResponse update(MemberUpdateBundle bundle) {
        Member member = memberRepository.findById(bundle.getId())
                .orElseThrow(() -> {
                    throw new NotFoundException(ErrorMessage.NOT_EXIST_MEMBER);
                });
        member.update(bundle.getName(), bundle.getAge(), bundle.getHobby());
        return memberConverter.toMemberUpdateResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberDetailResponse findById(MemberFindBundle bundle) {
        return memberRepository.findById(bundle.getId())
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