package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.board.global.exception.ErrorCode.DUPLICATE_EMAIL;
import static com.example.board.global.exception.ErrorCode.MEMBER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse createMember(MemberCreateRequest request) {
        validateDuplicateEmail(request.email());
        Member member = memberRepository.save(request.toEntity());
        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberById(Long id) {
        Member member = getMemberEntity(id);
        return MemberResponse.from(member);
    }

    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberResponse::from).toList();
    }

    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
        Member member = getMemberEntity(id);
        member.update(request.name(), request.hobby());
        return MemberResponse.from(member);
    }

    public void deleteMemberById(Long id) {
        getMemberEntity(id);
        memberRepository.deleteById(id);
    }

    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }

    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsMemberByEmail(email)) {
            throw new BusinessException(DUPLICATE_EMAIL);
        }
    }

    private Member getMemberEntity(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(MEMBER_NOT_FOUND));
    }
}
