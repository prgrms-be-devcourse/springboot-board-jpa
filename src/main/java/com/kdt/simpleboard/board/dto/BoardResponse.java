package com.kdt.simpleboard.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@NoArgsConstructor(access = PRIVATE)
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
