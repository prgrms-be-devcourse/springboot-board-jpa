package com.example.board.domain.member.service;

import com.example.board.domain.member.dto.MemberCreateRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.dto.MemberUpdateRequest;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponse createMember(MemberCreateRequest request) {
        validateDuplicateEmail(request.email());
        Member member = memberRepository.save(request.toEntity());
        return MemberResponse.from(member);
    }

    public MemberResponse findMemberById(Long id) {
        Member member = getMemberEntity(id);
        return MemberResponse.from(member);
    }
    
    public List<MemberResponse> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(MemberResponse::from).toList();
    }

    @Transactional
    public MemberResponse updateMember(Long id, MemberUpdateRequest request) {
        Member member = getMemberEntity(id);
        member.update(request.name(), request.hobby());
        return MemberResponse.from(member);
    }

    @Transactional
    public void deleteMemberById(Long id) {
        getMemberEntity(id);
        memberRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllMembers() {
        memberRepository.deleteAll();
    }

    private void validateDuplicateEmail(String email) {
        if (!memberRepository.existsMemberByEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    private Member getMemberEntity(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }
}
