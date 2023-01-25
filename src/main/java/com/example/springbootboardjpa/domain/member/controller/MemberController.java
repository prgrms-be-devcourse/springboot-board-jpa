package com.example.springbootboardjpa.domain.member.controller;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.domain.member.repository.MemberRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

  private final MemberRepository memberRepository;

  public MemberController(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  @GetMapping("/api/v1/member")
  public void test() {
    Member member = new Member("장주영", 25, "풋살");
    memberRepository.save(member);
  }

}
