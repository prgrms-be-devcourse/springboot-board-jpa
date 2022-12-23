package com.prgrms.board.service;

import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.dto.response.MemberResponseDto;

import java.util.List;

public interface MemberService {
    Long join(MemberCreateDto createDto);

    MemberResponseDto findById(Long memberId);

    List<MemberResponseDto> findAll();
}
