package com.example.board.global.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record PageCustomResponse<T>(List<T> content,
                                    PageableCustom pageInfo) {

    public static <T> PageCustomResponse<T> of(List<T> stock, Pageable pageable, long totalElement) {
        return new PageCustomResponse<>(stock, PageableCustom.of(new PageImpl<>(stock, pageable, totalElement)));
    }
}
