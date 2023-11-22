package com.example.board.dto.request.post;

import java.time.LocalDateTime;

public record PostSearchCondition(

        LocalDateTime createdAtFrom,

        LocalDateTime createdAtTo,

        SortType sortType
) {
}
