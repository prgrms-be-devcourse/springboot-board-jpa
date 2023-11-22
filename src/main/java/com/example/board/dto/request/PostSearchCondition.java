package com.example.board.dto.request;

import java.time.LocalDateTime;

public record PostSearchCondition(LocalDateTime createdAtFrom, LocalDateTime createdAtTo) {
}
