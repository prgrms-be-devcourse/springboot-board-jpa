package com.prgrms.board.controller;

import com.prgrms.board.dto.ApiResponse;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;
import com.prgrms.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {
    private final MemberService memberService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Long> createMember(@RequestBody @Valid MemberCreateDto createDto) {
        Long savedMemberId = memberService.join(createDto);
        return ApiResponse.created(savedMemberId);
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MemberResponseDto> findById(@PathVariable Long id) {
        MemberResponseDto responseDto = memberService.findById(id);
        return ApiResponse.ok(responseDto);
    }
}
