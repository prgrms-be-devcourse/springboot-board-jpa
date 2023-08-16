package com.seungwon.board.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seungwon.board.member.application.MemberService;
import com.seungwon.board.member.application.dto.MemberRequestDto;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping()
	ResponseEntity<Long> create(@RequestBody MemberRequestDto memberRequestDto) {
		Long id = memberService.create(memberRequestDto);
		return ResponseEntity.ok(id);
	}

}
