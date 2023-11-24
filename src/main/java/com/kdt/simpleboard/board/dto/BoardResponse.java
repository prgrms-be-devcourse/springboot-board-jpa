package com.kdt.simpleboard.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class BoardResponse {

    public record CreateBoardResponse(
            Long createdId
    )
    {}

    @Builder
    public record FindBoardResponse(
            Long userId,
            String title,
            String content
    ){}
}
