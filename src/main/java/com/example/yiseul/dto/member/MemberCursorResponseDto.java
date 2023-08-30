package com.example.yiseul.dto.member;

import java.util.List;

public record MemberCursorResponseDto (
  List<MemberResponseDto> memberResponseDto,
  Long cursorId
) {
}
