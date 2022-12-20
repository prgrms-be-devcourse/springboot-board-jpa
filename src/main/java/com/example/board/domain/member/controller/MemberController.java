package com.example.board.domain.member.controller;

import com.example.board.domain.member.dto.MemberRequest;
import com.example.board.domain.member.dto.MemberResponse;
import com.example.board.domain.member.service.MemberService;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }

  @GetMapping("/{memberId}")
  public ResponseEntity<MemberResponse.Detail> mypage(@PathVariable Long memberId) {
    MemberResponse.Detail detail = memberService.findById(memberId);

    return ResponseEntity.ok(detail);
  }

  @PostMapping
  public ResponseEntity<Void> newMember(@RequestBody MemberRequest memberRequest) {
    Long savedId = memberService.save(memberRequest);

    return ResponseEntity
        .created(
            URI.create(String.format("/member/%d", savedId)))
        .build();
  }
}
