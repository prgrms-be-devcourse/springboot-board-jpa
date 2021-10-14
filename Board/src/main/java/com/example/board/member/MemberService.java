package com.example.board.member;

public interface MemberService{

    MemberDto createMember(MemberDto memberDto);
    MemberDto updateMember(MemberDto memberDto);
    RuntimeException deleteMember(String name);
    MemberDto findByName(String name);
}
