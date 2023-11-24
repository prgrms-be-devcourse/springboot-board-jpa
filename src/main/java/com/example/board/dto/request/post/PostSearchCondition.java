package com.example.board.dto.request.post;

import java.time.LocalDate;

public record PostSearchCondition(

        LocalDate createdAtFrom,

        LocalDate createdAtTo,

        SortType sortType
) {
}
