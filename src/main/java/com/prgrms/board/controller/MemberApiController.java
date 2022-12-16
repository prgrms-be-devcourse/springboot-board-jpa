package com.prgrms.board.controller;

import com.prgrms.board.dto.ApiResponse;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;
import com.prgrms.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping
    public ApiResponse<Long> createMember(@RequestBody @Valid MemberCreateDto createDto) {
        Long savedMemberId = memberService.join(createDto);
        return ApiResponse.ok(savedMemberId);
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponseDto> findById(@PathVariable Long id) {
        MemberResponseDto responseDto = memberService.findById(id);

        return ApiResponse.ok(responseDto);
    }
}
