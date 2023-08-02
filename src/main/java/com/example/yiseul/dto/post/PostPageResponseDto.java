package com.example.yiseul.dto.post;

import java.util.List;

public record PostPageResponseDto(List<PostResponseDto> postResponseDto,
                                  int pageNumber,
                                  int pageSize,
                                  int totalPages,
                                  long totalElements,
                                  boolean isFirst,
                                  boolean isLast) {
}
