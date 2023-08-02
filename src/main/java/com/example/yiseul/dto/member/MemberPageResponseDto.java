package com.example.yiseul.dto.member;

import java.util.List;

public record MemberPageResponseDto(List<MemberResponseDto> memberResponseDto,
                                    int pageNumber,
                                    int pageSize,
                                    int totalPages,
                                    long totalElements,
                                    boolean isFirst,
                                    boolean isLast) {
}
