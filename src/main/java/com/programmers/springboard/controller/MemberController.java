package com.programmers.springboard.controller;

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

import com.programmers.springboard.request.MemberCreateRequest;
import com.programmers.springboard.request.MemberLoginRequest;
import com.programmers.springboard.request.MemberUpdateRequest;
import com.programmers.springboard.response.ApiResponse;
import com.programmers.springboard.response.MemberLoginResponse;
import com.programmers.springboard.response.MemberResponse;
import com.programmers.springboard.service.MemberService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@PostMapping("/sign-in")
	public ResponseEntity<ApiResponse<MemberLoginResponse>> login(@Valid @RequestBody MemberLoginRequest request) {
		MemberLoginResponse response = memberService.login(request);
		return ResponseEntity.ok(ApiResponse.ok(response));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<MemberResponse>> getMember(@PathVariable Long id) {
		MemberResponse memberResponse = memberService.getMemberById(id);
		return ResponseEntity.ok(ApiResponse.ok(memberResponse));
	}

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

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<MemberResponse>> updateMember(@PathVariable Long id,
		@Valid @RequestBody MemberUpdateRequest memberUpdateRequest) {
		MemberResponse member = memberService.updateMember(id, memberUpdateRequest);
		return ResponseEntity.ok(ApiResponse.ok(member));
	}

	@DeleteMapping
	public ResponseEntity<ApiResponse<Void>> deleteMembers(@RequestParam List<Long> ids) {
		memberService.deleteMembers(ids);
		return ResponseEntity.ok(ApiResponse.noContent());
	}
}
