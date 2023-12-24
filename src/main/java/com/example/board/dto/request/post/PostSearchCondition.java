package com.example.board.dto.request.post;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PostSearchCondition(

        LocalDate createdAtFrom,

        LocalDate createdAtTo,

        SortType sortType
) {
    public LocalDateTime startOfDayFrom() {
        return createdAtFrom.atStartOfDay();
    }

    public LocalDateTime endOfDayTo() {
        return createdAtTo.atTime(23, 59, 59);
    }
}
