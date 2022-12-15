package com.prgrms.board.service;

import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.MemberResponseDto;

import java.util.List;

public interface MemberService {
    Long join(MemberCreateDto createDto);

    MemberResponseDto findById(Long memberId);

    List<MemberResponseDto> findAll();
}
