package com.example.board.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JPAMemberService implements MemberService{

    MemberJPARepository repository;
    MemberConverter converter;

    public JPAMemberService(MemberJPARepository repository, MemberConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Transactional
    @Override
    public MemberDto createMember(MemberDto memberDto) {
        if (repository.existsByName(memberDto.name())){
            return null;
        }
        Member member = converter.of(memberDto);
        repository.save(member);
        memberDto = converter.toDto(member);
        return memberDto;
    }

    @Transactional
    @Override
    public MemberDto updateMember(MemberDto memberDto) {
        Member member = converter.of(memberDto);
        return memberDto;
    }

    @Transactional
    @Override
    public RuntimeException deleteMember(String name) {
        if (!repository.existsByName(name)) {
            return new RuntimeException("No member");
        }
        repository.deleteByName(name);
        return null;
    }

    @Transactional
    @Override
    public MemberDto findByName(String name) {
        if (!repository.existsByName(name)){
            return null;
        }
        MemberDto memberDto = converter.toDto(repository.findByName(name).get());
        return memberDto;
    }
}
