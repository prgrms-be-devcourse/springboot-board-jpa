package com.programmers.springboard.member.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.programmers.springboard.member.dto.MemberCreateRequest;
import com.programmers.springboard.member.dto.MemberLoginRequest;
import com.programmers.springboard.member.dto.MemberUpdateRequest;
import com.programmers.springboard.global.common.ApiResponse;
import com.programmers.springboard.member.dto.MemberLoginResponse;
import com.programmers.springboard.member.dto.MemberResponse;
import com.programmers.springboard.member.service.MemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "회원 컨트롤러")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@Operation(summary = "회원 로그인", description = "회원의 로그인 정보를 검증하고 토큰을 반환합니다.")
	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponse<MemberLoginResponse>> login(@Valid @RequestBody MemberLoginRequest request) {
		MemberLoginResponse response = memberService.login(request);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@Operation(summary = "회원 조회", description = "ID를 기반으로 특정 회원의 정보를 조회합니다.")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<MemberResponse>> getMember(@PathVariable Long id) {
		MemberResponse memberResponse = memberService.getMemberById(id);
		return ResponseEntity.ok(ApiResponse.ok(memberResponse));
	}

	@Operation(summary = "회원 가입", description = "새로운 회원을 생성합니다.")
	@PostMapping
	public ResponseEntity<ApiResponse<MemberResponse>> createMember(
		@Valid @RequestBody MemberCreateRequest memberCreateRequest) {
		MemberResponse member = memberService.createMember(memberCreateRequest);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequestUri()
			.path("/{id}")
			.buildAndExpand(member.id())
			.toUri();
		return ResponseEntity.created(location).body(ApiResponse.created(member));
	}

	@Operation(summary = "회원 정보 업데이트", description = "ID를 기반으로 특정 회원의 정보를 업데이트합니다.")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MemberResponse>> updateMember(@PathVariable Long id,
		@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
		MemberResponse member = memberService.updateMember(id, memberUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok(member));
	}

	@Operation(summary = "회원 삭제", description = "하나 이상의 회원을 삭제합니다.")
	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteMembers(@RequestParam List<Long> ids) {
		memberService.deleteMembers(ids);
		return ResponseEntity.ok(ApiResponse.noContent());
	}
}
